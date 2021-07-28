package de.example.crawler.utils;

import com.github.kevinsawicki.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConnectUtils.class.getSimpleName());

    private static final int DEFAULT_TIMEOUT = 60 * 1000; // ms
    private static final String USER_AGENT = "CryptoNews Scraper (compatible; Mozilla/5.0; https://example.com))";

    public static Document getHTML(String targetUrl) {
        HttpRequest request = prepareRequest(targetUrl);
        return Jsoup.parse(request.body(), request.url().toString(), Parser.htmlParser());
    }

    private static HttpRequest prepareRequest(String targetUrl) {
        HttpRequest request = HttpRequest.get(targetUrl)
                .connectTimeout(DEFAULT_TIMEOUT)
                .readTimeout(DEFAULT_TIMEOUT)
                .userAgent(USER_AGENT)
                .followRedirects(true);

        int status = request.code();
        if (status == 301 || status == 302) {
            logger.info("{}: URL redirects to {}, following redirect", targetUrl, request.location());
            return prepareRequest(request.location());
        }

        return request;
    }
}
