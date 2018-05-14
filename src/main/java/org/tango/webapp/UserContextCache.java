package org.tango.webapp;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * This class maintains it is own cache of the entries and 
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 5/14/18
 */
public class UserContextCache implements UserContextStorage {
    private final UserContextStorage delegate;

    private final Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1000L)
            .recordStats()
            .build(
                    new CacheLoader<String, String>() {
                        @Override
                        public String load(String key) throws Exception {
                            return delegate.load(key);
                        }
                    }
            );
    

    public UserContextCache(UserContextStorage delegate) {
        this.delegate = delegate;
    }

    @Override
    public void save(String userName, String data) throws IOException {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public void delete(String userName) throws IOException {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }

    @Override
    public String load(String userName) throws IOException {
        throw new UnsupportedOperationException("This method is not supported in " + this.getClass());
    }
}
