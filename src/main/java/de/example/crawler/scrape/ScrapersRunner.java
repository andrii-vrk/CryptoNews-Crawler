package de.example.crawler.scrape;

import de.example.crawler.repository.ArticleRepository;
import de.example.crawler.scrape.scrapers.CryptoPotatoScraper;
import de.example.crawler.utils.FutureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ScrapersRunner {

    private static final Logger logger = LoggerFactory.getLogger(ScrapersRunner.class.getSimpleName());

    private final ArticleRepository repository;
    private final List<AbstractScraper> scrapers;


    public ScrapersRunner(final ArticleRepository repository) {
        this.repository = repository;
        this.scrapers = Arrays.asList(
                new CryptoPotatoScraper()
        );
    }

    public CompletableFuture<List<Void>> scrape() {
        return FutureUtils.sequence(scrapers.stream()
                .map(scraper -> CompletableFuture.supplyAsync(scraper::startScrapping))
                .map(articlesFuture -> articlesFuture
                        .thenApply(scrapeResults -> {
                            // remove null entities
                            scrapeResults.getArticles().removeAll(Collections.singleton(null));

                            if (scrapeResults.getArticles().size() == 0) {
                                logger.error("{}: Scrapped 0 articles, possible website changed.", scrapeResults.getScraperName());
                            }

                            return scrapeResults;
                        })
                        .thenAccept(repository::addArticles)
                        .exceptionally(e -> {
                            logger.error("Error scraping", e);
                            return null;
                        }))
                .collect(Collectors.toList()));
    }
}
