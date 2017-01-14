package com.assistant.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "rss")
public class RssEntity {
    private String url;
    private String title;
    private Timestamp lastUpdated;

    @Id
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RssEntity rssEntity = (RssEntity) o;

        if (url != null ? !url.equals(rssEntity.url) : rssEntity.url != null) return false;
        if (title != null ? !title.equals(rssEntity.title) : rssEntity.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "last_updated")
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
