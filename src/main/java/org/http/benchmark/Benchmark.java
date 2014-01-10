package org.http.benchmark;

import com.google.common.collect.Lists;
import org.http.benchmark.loaders.ApacheHttpClient4x;
import org.http.benchmark.loaders.CommonsHttpClient;
import org.http.benchmark.loaders.UrlConnectionLoader;

import java.io.IOException;
import java.util.List;

/**
 * @author Denis Khurtin
 */
public class Benchmark {

    public static final int ATTEMPTS = 5;

    public static final String SMALL_RESOURCE_URI = //"http://192.168.1.36/share/gwt-dev-2.5.1.pom";
            "http://repo1.maven.org/maven2/com/google/gwt/gwt-dev/2.5.1/gwt-dev-2.5.1.pom"; // 778 B

    public static final String MEDIUM_RESOURCE_URI = //"http://192.168.1.36/share/gwt-dev-2.5.1-sources.jar";
            "http://repo1.maven.org/maven2/com/google/gwt/gwt-dev/2.5.1/gwt-dev-2.5.1-sources.jar"; // 2 Mb

    public static final String BIG_RESOURCE_URI = //"http://192.168.1.36/share/gwt-dev-2.5.1.jar";
            "http://repo1.maven.org/maven2/com/google/gwt/gwt-dev/2.5.1/gwt-dev-2.5.1.jar"; // 32.3 Mb

    public static void main(String[] args) throws IOException {

        List<UrlLoader> urlLoaders = Lists.newArrayList(
                new UrlConnectionLoader(),
                new CommonsHttpClient(),
                new ApacheHttpClient4x()
        );

        for (UrlLoader urlLoader : urlLoaders) {
            for (int attempt = 0; attempt < ATTEMPTS; ++attempt) {
                tryLoad(urlLoader, BIG_RESOURCE_URI);
            }
        }
    }

    private static long tryLoad(UrlLoader urlLoader, String uri) throws IOException {
        long before = System.currentTimeMillis();
        urlLoader.loadToNull(uri);
        long after = System.currentTimeMillis();

        System.out.printf("%s has taken %.2f sec.\n", urlLoader.getClass().getSimpleName(), (after - before) / 1000.0);

        return after - before;
    }
}
