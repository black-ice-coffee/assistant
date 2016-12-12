package com.assistant.model;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable {
    public String link;
    public String title;
    public String author;
    public Date publishDate;
    public String summary;
    public String content;
}
