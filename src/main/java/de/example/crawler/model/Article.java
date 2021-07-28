package de.example.crawler.model;

import java.util.Date;

public class Article {

    private final int id;
    private final int sourceId;
    private final Date date;
    private final String title;
    private final String url;
    private final String description;
    private final String content;
    private final String previewImageUrl;

    public Article(int id, int sourceId, Date date, String title, String url, String description, String content, String previewImageUrl) {
        this.id = id;
        this.sourceId = sourceId;
        this.date = date;
        this.title = title;
        this.url = url;
        this.description = description;
        this.content = content;
        this.previewImageUrl = previewImageUrl;
    }

    public int getId() {
        return id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", previewImageUrl='" + previewImageUrl + '\'' +
                '}';
    }
}
