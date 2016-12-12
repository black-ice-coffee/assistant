package com.assistant.repository;

import com.assistant.entity.RssGroupItemEntity;
import com.assistant.entity.RssGroupItemEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RssGroupItemRepository extends JpaRepository<RssGroupItemEntity, RssGroupItemEntityPK>{
    List<RssGroupItemEntity> findById(String id);
}
