package com.assistant.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RSSGroup implements Serializable {
    public String name;
    public List<RSSItem> items = new ArrayList<>();
}
