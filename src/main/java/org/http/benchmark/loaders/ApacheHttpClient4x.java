package org.http.benchmark.loaders;

import com.google.common.io.Closer;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.http.benchmark.UrlLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Denis Khurtin
 */
public class ApacheHttpClient4x implements UrlLoader {

    @Override
    public void loadToNull(String uri) throws IOException {
        Closer closer = Closer.create();

        try {
            CloseableHttpClient httpClient = closer.register(HttpClients.custom().disableContentCompression().build());
            HttpGet httpGet = new HttpGet(uri);

            CloseableHttpResponse response = closer.register(httpClient.execute(httpGet));

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new RuntimeException("Unsuccessful request to " + httpGet + "\n" +
                        "Response status: " + response.getStatusLine());
            }

            InputStream inputStream = closer.register(response.getEntity().getContent());

            byte[] buffer = new byte[BUFFER_SIZE];

            for (int n; (n = inputStream.read(buffer)) > 0; ) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Current thread should be interrupted.");
                }
            }
        } catch (Throwable t) {
            throw closer.rethrow(t);
        } finally {
            closer.close();
        }
    }
}
