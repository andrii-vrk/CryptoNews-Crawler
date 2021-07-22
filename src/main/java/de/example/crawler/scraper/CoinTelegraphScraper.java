package de.example.crawler.scraper;

import com.github.kevinsawicki.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinTelegraphScraper {

    public CoinTelegraphScraper() {

    }

    private static final Logger logger = LoggerFactory.getLogger(CoinTelegraphScraper.class.getSimpleName());

    private static HttpRequest prepareRequest(String url) {
        HttpRequest request = HttpRequest.get(url)
                .connectTimeout(60 * 1000)
                .readTimeout(60 * 1000)
                .userAgent("CryptoNews (compatible; Mozilla/5.0; https://example.org)")
                .followRedirects(true);

        int status = request.code();
        if (status == 302 || status == 301) {
            return prepareRequest(request.location());
        }

        return request;
    }

    public Document getHTMLDocument(String url) {
        HttpRequest request = prepareRequest(url);

        return Jsoup.parse(request.body(), request.url().toString(), Parser.htmlParser());
    }


}
