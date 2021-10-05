package de.example.crawler.scrape.scrapers;

import de.example.crawler.model.Article;
import de.example.crawler.scrape.AbstractScraper;
import de.example.crawler.utils.ConnectUtils;
import de.example.crawler.utils.FutureUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CoinNewsScraper extends AbstractScraper {

    private static final Logger logger = LoggerFactory.getLogger(CoinNewsScraper.class.getSimpleName());

    private static final int SOURCE_ID = 1;
    private static final String ROOT_URL = "https://cryptonews.com";

    @Override
    public List<Article> scrape() {
        Document document = ConnectUtils.getHTML(ROOT_URL + "/news/");

        return procDocument(document);
    }


    public List<Article> procDocument(Document document) {
        Elements rawArticles = document.select("#list-items [id*=post]");

        return FutureUtils.processListAsync(rawArticles, this::parse);
    }

    private Article parse(Element element) {
        final Element titleEl = element.select("a").first();
        final String title = titleEl.attr("title");
        final String url = titleEl.attr("abs:href");
        final String imageUrl = titleEl.select("img").attr("src");

        final Element rawDateEl = element.select("div.entry-meta").first();
        final String day = rawDateEl.select("time").attr("datetime");
        final String time = rawDateEl.select("span.entry-time").text();
        final Date date = parseDate(day + " " + time, Locale.ENGLISH);
        final String description = element.select("div.entry-excerpt p").text();
        final String content = getFullArticle(url);

        return new Article(sourceId, date, title, url, description, content, imageUrl);
    }

    @Override
    public String getDateFormat(Locale locale) {
        return null;
    }
}
