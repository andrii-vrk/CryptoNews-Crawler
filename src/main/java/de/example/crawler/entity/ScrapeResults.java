package de.example.crawler.entity;

import de.example.crawler.model.Article;

import java.util.List;

public class ScrapeResults {

    private final String scraperName;
    private final List<Article> articles;

    public ScrapeResults(String scraperName, List<Article> articles) {
        this.scraperName = scraperName;
        this.articles = articles;
    }

    public String getScraperName() {
        return scraperName;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
