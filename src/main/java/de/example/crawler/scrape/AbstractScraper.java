package de.example.crawler.scrape;

import de.example.crawler.entity.ScrapeResults;
import de.example.crawler.model.Article;
import de.example.crawler.utils.ConnectUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public abstract class AbstractScraper {

    protected Logger logger = LoggerFactory.getLogger(AbstractScraper.class.getSimpleName());

    public abstract List<Article> scrape();

    public abstract String getDateFormat(Locale locale);

    protected Date parseDate(String rawDate, Locale locale) {
        if (rawDate == null || rawDate.isEmpty()) {
            return new Date();
        }

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getDateFormat(locale), locale);
        try {
            return simpleDateFormat.parse(rawDate.replace("\u00a0", " ").trim());
        } catch (ParseException e) {
            logger.error("Error parsing date", e);
            return new Date();
        }
    }

    protected String getFullArticle(final String url) {
        try {
            Document document = ConnectUtils.getHTML(url);
            return getFullArticle(document);
        } catch (Exception e) {
            logger.warn("Can not fetch page content: {} - {}", e.getMessage(), url);
            return null;
        }
    }

    protected String getFullArticle(final Document document) {
        return null;
    }


}
