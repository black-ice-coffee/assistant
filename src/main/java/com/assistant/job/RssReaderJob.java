package com.assistant.job;

import com.assistant.TextRank;
import com.assistant.entity.NewsEntity;
import com.assistant.entity.RssEntity;
import com.assistant.entity.RssGroupItemEntity;
import com.assistant.repository.NewsRepository;
import com.assistant.repository.RssGroupItemRepository;
import com.assistant.repository.RssRepository;
import com.assistant.service.RssSummaryService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

@Component
public class RssReaderJob {
    private static final Logger log = LoggerFactory.getLogger(RssReaderJob.class);

    @Autowired
    private RssGroupItemRepository rssGroupItemRepository;

    @Autowired
    private RssRepository rssRepository;

    @Autowired
    private NewsRepository newsRepository;

    private TextRank rank = new TextRank();
    private ArticleExtractor ae = new ArticleExtractor();

    @Scheduled(fixedRate = 3600000)
    public void reportCurrentTime() {
        // sync rss link
        log.info("Synchronize rss");
        syncRss();

        // foreach rss: read and save data
        List<RssEntity> rssEntities = rssRepository.findAll();
        for(RssEntity rssEntity: rssEntities){
            try {
                log.info("Processing URL: " + rssEntity.getUrl());
                readRss(rssEntity);
                rssEntity.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                rssRepository.save(rssEntity);
            } catch (Exception e) {
                log.error("Failed to process URL: " + rssEntity.getUrl(), e);
            }
        }
        log.info("Done!");
    }

    private void readRss(RssEntity rssEntity) throws IOException, FeedException {
        URL feedUrl = new URL(rssEntity.getUrl());
        HttpURLConnection httpcon = (HttpURLConnection)feedUrl.openConnection();
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(httpcon));

        if(rssEntity.getLastUpdated() != null && rssEntity.getLastUpdated().after(feed.getPublishedDate())){
            log.info("Already updated");
            return;
        }

        List<NewsEntity> newsEntities = new ArrayList<>();
        List entries = feed.getEntries();
        Iterator itEntries = entries.iterator();
        while (itEntries.hasNext()) {
            SyndEntry entry = (SyndEntry) itEntries.next();
            try {
                NewsEntity newsEntity = getNews(entry);
                if(rssEntity.getLastUpdated() == null || rssEntity.getLastUpdated().before(newsEntity.getPublishedDate())){
                    newsEntity.setRssUrl(rssEntity.getUrl());
                    newsEntities.add(newsEntity);
                }
            } catch (BoilerpipeProcessingException e) {
                e.printStackTrace();
            }
        }

        newsRepository.save(newsEntities);
    }

    private NewsEntity getNews(SyndEntry entry) throws MalformedURLException, BoilerpipeProcessingException {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setTitle(entry.getTitle());
        newsEntity.setUrl(entry.getLink());
        newsEntity.setAuthor(entry.getAuthor());
        newsEntity.setPublishedDate(new Timestamp(entry.getPublishedDate().getTime()));
        String content = ae.getText(new URL(newsEntity.getUrl()));
        newsEntity.setContent(content);
        newsEntity.setSummary(rank.summary(content));
        return newsEntity;
    }

    private void syncRss(){
        List<RssGroupItemEntity> itemEntities = rssGroupItemRepository.findAll();

        List<RssEntity> rssEntities = rssRepository.findAll();
        Dictionary<String, RssEntity> rssMap = new Hashtable<>(rssEntities.size());
        for(RssEntity entity: rssEntities){
            rssMap.put(entity.getUrl(),entity);
        }

        List<RssEntity> newEntites = new ArrayList<>();
        for(RssGroupItemEntity itemEntity: itemEntities){
            if(rssMap.get(itemEntity.getUrl()) == null){
                RssEntity rssEntity = new RssEntity();
                rssEntity.setUrl(itemEntity.getUrl());
                rssEntity.setTitle(itemEntity.getTitle());
                rssMap.put(rssEntity.getUrl(), rssEntity);
                newEntites.add(rssEntity);
            }
        }
        if(newEntites.size() > 0){
            rssRepository.save(newEntites);
        }
    }
}
