package de.example.crawler.utils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FutureUtils {
    public static <T, R> List<R> processListAsync(final Collection<T> list, final Function<T, R> function) {
        List<CompletableFuture<R>> futures = list.stream()
                .map(rawArticle -> CompletableFuture.supplyAsync(() -> function.apply(rawArticle)).exceptionally(e -> {
                    throw new RuntimeException(e);
                }))
                .collect(Collectors.toList());

        return sequence(futures).join();
    }

    public static <T, R> List<R> processList(final Collection<T> list, final Function<T, R> function) {
        return list.stream().map(function).collect(Collectors.toList());
    }

    public static <T> CompletableFuture<List<T>> sequence(final List<CompletableFuture<T>> list) {
        return CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]))
                .thenApply(v -> list.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }
}
