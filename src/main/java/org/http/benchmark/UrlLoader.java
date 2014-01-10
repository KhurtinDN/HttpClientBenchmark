package org.http.benchmark;

import java.io.IOException;

/**
 * @author Denis Khurtin
 */
public interface UrlLoader {
    static final int BUFFER_SIZE = 8 * 1024;

    void loadToNull(String uri) throws IOException;
}
