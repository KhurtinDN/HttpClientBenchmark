package org.http.benchmark;

import org.http.benchmark.loaders.ApacheHttpClient4x;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Denis Khurtin
 */
public class Temp {

    private static final Logger LOGGER = LoggerFactory.getLogger(Temp.class);

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                UrlLoader urlLoader = new ApacheHttpClient4x();
                try {
                    urlLoader.loadToNull(Benchmark.BIG_RESOURCE_URI);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    if (e.getCause() instanceof InterruptedException) {
                        LOGGER.warn("Exception.", e.getCause());
                    }
                }
            }
        });

        thread.start();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("interrupting");
        thread.interrupt();
        System.out.println("interrupted");


        thread.join();
    }
}
