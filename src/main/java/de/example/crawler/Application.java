package de.example.crawler;

import de.example.crawler.repository.DefaultArticleRepository;
import de.example.crawler.scrape.ScrapersRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class.getSimpleName());

    public static void main(String[] args) {
        logger.info("started.");
        ScrapersRunner runner = new ScrapersRunner(new DefaultArticleRepository());
        runner.scrape().join();
    }
}