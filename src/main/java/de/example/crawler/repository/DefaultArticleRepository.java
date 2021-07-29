package de.example.crawler.repository;

import de.example.crawler.entity.ScrapeResults;
import de.example.crawler.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DefaultArticleRepository extends Repository implements ArticleRepository {

    private static final Logger logger = LoggerFactory.getLogger(DefaultArticleRepository.class.getSimpleName());

    private static final String INSERT_STATEMENT =
            "INSERT INTO public.article " +
                    "(source_id, title, url, description, content, image_url, date)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)";

    @Override
    public void addArticles(ScrapeResults results) {
        try (
                final Connection connection = this.getConnection();
                final PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT);
        ) {
            logger.info("{}: new articles were scraped", results.getArticles().size());
            for (Article item : results.getArticles()) {
                {
                    logger.info("{}: Inserting article by url {}", results.getScraperName(), item.getUrl());
                    statement.setInt(1, item.getSourceId());
                    statement.setString(2, item.getTitle());
                    statement.setString(3, item.getUrl());
                    statement.setString(4, item.getDescription());
                    statement.setString(5, item.getContent());
                    statement.setString(6, item.getImageUrl());
                    statement.setTimestamp(7, new java.sql.Timestamp(item.getDate().getTime()));

                    statement.addBatch();
                }
            }
            statement.executeBatch();
        } catch (SQLException e) {
            logger.error("Database error", e);
        }
    }
}
