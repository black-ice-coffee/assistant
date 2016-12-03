package com.assistant.service;

import com.assistant.TextRank;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RssSummaryService {

    private TextRank rank = new TextRank();
    private ArticleExtractor ae = new ArticleExtractor();

    public List<Summary> getSummaries(String url) throws IOException, FeedException, BoilerpipeProcessingException {
        List<Summary> summaries = new ArrayList<>();

        URL feedUrl = new URL(url);
        HttpURLConnection httpcon = (HttpURLConnection)feedUrl.openConnection();
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(httpcon));
        List entries = feed.getEntries();
        Iterator itEntries = entries.iterator();

        while (itEntries.hasNext()) {
            SyndEntry entry = (SyndEntry) itEntries.next();
            Summary summary = getSummary(entry);
            summaries.add(summary);
        }

        return summaries;
    }

    private Summary getSummary(SyndEntry entry) throws MalformedURLException, BoilerpipeProcessingException {
        Summary summary = new Summary();
        summary.title = entry.getTitle();
        summary.link = entry.getLink();
        summary.author = entry.getAuthor();
        summary.publishDate = entry.getPublishedDate();
        String content = ae.getText(new URL(summary.link));
        //summary.content = content;
        summary.summary = rank.summary(content);
        return summary;
    }

    public static class Summary implements Serializable{
        public String link;
        public String title;
        public String author;
        public Date publishDate;
        public String summary;
        public String content;
    }
}
