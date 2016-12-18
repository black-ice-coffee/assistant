package com.assistant.model;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    public String content;
    public String title;
    public String id;
    public Date modifiedDate;
    public Date createdDate;
}
