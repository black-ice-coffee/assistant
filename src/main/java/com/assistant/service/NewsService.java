package com.assistant.service;

import com.assistant.entity.NewsEntity;
import com.assistant.model.News;
import com.assistant.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public List<News> getNewsOfRss(String rssUrl){
        List<NewsEntity> newsEntities = newsRepository.findByRssUrlOrderByPublishedDateDesc(rssUrl);
        List<News> newsItems = new ArrayList<>();
        for(NewsEntity entity: newsEntities){
            newsItems.add(toDto(entity));
        }
        return newsItems;
    }

    private News toDto(NewsEntity entity){
        News news = new News();
        news.author = entity.getAuthor();
        news.content = entity.getContent();
        news.link = entity.getUrl();
        news.publishDate = entity.getPublishedDate();
        news.summary = entity.getSummary();
        news.title = entity.getTitle();
        return news;
    }
}
