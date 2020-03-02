package com.assistant.service;

import com.assistant.Helper;
import com.assistant.entity.RssGroupEntity;
import com.assistant.entity.RssGroupItemEntity;
import com.assistant.model.RSSGroup;
import com.assistant.model.RSSItem;
import com.assistant.repository.RssGroupItemRepository;
import com.assistant.repository.RssGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RssConfigurationService {

    @Autowired
    private RssGroupRepository rssGroupRepository;

    @Autowired
    private RssGroupItemRepository rssGroupItemRepository;

    public List<RSSGroup> getRssGroups() {
        List<RssGroupEntity> groupEntities = rssGroupRepository.findAll();
        List<RSSGroup> groups = new ArrayList<>();
        for (RssGroupEntity groupEntity : groupEntities) {
            List<RssGroupItemEntity> itemEntities = rssGroupItemRepository.findById(groupEntity.getId());
            RSSGroup group = toDto(groupEntity, itemEntities);
            groups.add(group);
        }
        return groups;
    }

    public RSSGroup addGroup(RSSGroup group) {
        if (group.id == null) {
            RssGroupEntity groupEntity = new RssGroupEntity();
            groupEntity.setId(Helper.generateId());
            groupEntity.setTitle(group.name);
            rssGroupRepository.saveAndFlush(groupEntity);
        }
        return group;
    }

    public RSSGroup addUrl(String groupId, RSSItem item) {
        Optional<RssGroupEntity> groupEntity = rssGroupRepository.findById(groupId);
        if (groupEntity.isPresent()) {
            RssGroupItemEntity itemEntity = new RssGroupItemEntity();
            itemEntity.setId(groupId);
            itemEntity.setTitle(item.name);
            itemEntity.setUrl(item.url);
            rssGroupItemRepository.saveAndFlush(itemEntity);

            List<RssGroupItemEntity> itemEntities = rssGroupItemRepository.findById(groupEntity.get().getId());
            return toDto(groupEntity.get(), itemEntities);
        }
        return null;
    }

    public RSSGroup findGroup(String groupId) {
        Optional<RssGroupEntity> groupEntity = rssGroupRepository.findById(groupId);
        if (groupEntity.isPresent()) {
            List<RssGroupItemEntity> itemEntities = rssGroupItemRepository.findById(groupEntity.get().getId());
            return toDto(groupEntity.get(), itemEntities);
        }
        return null;
    }

    private RSSGroup toDto(RssGroupEntity groupEntity, List<RssGroupItemEntity> itemEntities) {
        RSSGroup group = new RSSGroup();
        group.id = groupEntity.getId();
        group.name = groupEntity.getTitle();

        for (RssGroupItemEntity itemEntity : itemEntities) {
            group.items.add(new RSSItem(itemEntity.getTitle(), itemEntity.getUrl()));
        }
        return group;
    }
}
