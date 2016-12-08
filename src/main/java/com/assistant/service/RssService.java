package com.assistant.service;

import com.assistant.Helper;
import com.assistant.model.RSSGroup;
import com.assistant.model.RSSItem;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RssService {

    List<RSSGroup> groups = new ArrayList<>();

    public RssService() {
        try {
            groups = Helper.loadFromResource("rss.json", new TypeReference<List<RSSGroup>>(){});
        } catch (Exception e){
            groups = new ArrayList<>();
        }
        //createSample();
    }

    public List<RSSGroup> getRssGroups(){
        return groups;
    }

    public RSSGroup addGroup(RSSGroup group){
        if(group.id == null) {
            group.id = Helper.generateId();
            groups.add(group);
        }
        return group;
    }

    public RSSGroup addUrl(String groupId, RSSItem item){
        RSSGroup group = findGroup(groupId);
        if(group != null){
            group.items.add(item);
        }
        return group;
    }

    private RSSGroup findGroup(String groupId){
        for(RSSGroup group: groups){
            if(group.id.equalsIgnoreCase(groupId)){
                if(group.items == null){
                    group.items = new ArrayList<>();
                }
                return group;
            }
        }
        return null;
    }

    private void createSample(){
        RSSGroup group = new RSSGroup();
        group.name = "VNExpress";
        group.items.add(new RSSItem("trang-chu", "http://vnexpress.net/rss/tin-moi-nhat.rss"));
        group.items.add(new RSSItem("thoi-su", "http://vnexpress.net/rss/thoi-su.rss"));
        group.items.add(new RSSItem("the-gioi", "http://vnexpress.net/rss/the-gioi.rss"));
        group.items.add(new RSSItem("kinh-doanh", "http://vnexpress.net/rss/kinh-doanh.rss"));
        groups.add(group);
    }
}
