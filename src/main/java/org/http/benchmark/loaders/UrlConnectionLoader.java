package org.http.benchmark.loaders;

import com.google.common.io.Closer;
import org.http.benchmark.UrlLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Denis Khurtin
 */
public class UrlConnectionLoader implements UrlLoader {

    @Override
    public void loadToNull(String uri) throws IOException {
        Closer closer = Closer.create();

        try {
            URL url = new URL(uri);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();

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
