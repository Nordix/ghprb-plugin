package org.jenkinsci.plugins.ghprb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

final class RegexUtil {

    private static final Logger LOGGER = Logger.getLogger(RegexUtil.class.getName());

    private RegexUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean find(Pattern pattern, String input, int timeoutMillis) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(() -> pattern.matcher(new ThreadString(input)).matches());
        try {
            return future.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            LOGGER.log(Level.INFO, "Unable to complete match detection " + pattern.pattern() , e);
            LOGGER.log(Level.INFO, input);
            future.cancel(true);
            return false;
        } finally {
            executor.shutdownNow();
        }
    }
}
