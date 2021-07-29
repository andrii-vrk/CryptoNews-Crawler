package de.example.crawler.repository;

import de.example.crawler.entity.ScrapeResults;

public interface ArticleRepository {

    void addArticles(final ScrapeResults results);

}
