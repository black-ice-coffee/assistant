package com.assistant.repository;

import com.assistant.entity.RssEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RssRepository extends JpaRepository<RssEntity, String> {
}
