package com.assistant.repository;

import com.assistant.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsEntity, String> {
    List<NewsEntity> findByRssUrl(String rssUrl);
}
