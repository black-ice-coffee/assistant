package com.assistant.service;

import com.assistant.Helper;
import com.assistant.model.RSSGroup;
import com.assistant.model.RSSItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RssService {

    List<RSSGroup> groups = new ArrayList<>();

    public RssService() {
        try {
            groups = Helper.loadFromResource("rss.json", groups.getClass());
        } catch (Exception e){
            groups = new ArrayList<>();
        }
        //createSample();
    }

    public List<RSSGroup> getRssGroups(){
        return groups;
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
