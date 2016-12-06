package com.assistant.model;

import java.io.Serializable;

public class RSSItem implements Serializable {
    public String name;
    public String url;

    public RSSItem(){}

    public RSSItem(String name, String url){
        this.name = name;
        this.url = url;
    }
}
