package org.http.benchmark.loaders;

import com.google.common.io.Closer;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.http.benchmark.UrlLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Denis Khurtin
 */
public class CommonsHttpClient implements UrlLoader {

    @Override
    public void loadToNull(String uri) throws IOException {
        Closer closer = Closer.create();

        try {
            GetMethod getMethod = new GetMethod(uri);

            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(getMethod);

            if (getMethod.getStatusCode() != 200) {
                throw new IllegalStateException("Get method return: " + getMethod.getStatusLine());
            }

            InputStream inputStream = closer.register(getMethod.getResponseBodyAsStream());

            byte[] buffer = new byte[BUFFER_SIZE];

            for (int n; (n = inputStream.read(buffer)) > 0; ) {
                // nothing
            }
        } catch (Throwable t) {
            throw closer.rethrow(t);
        } finally {
            closer.close();
        }
    }
}
