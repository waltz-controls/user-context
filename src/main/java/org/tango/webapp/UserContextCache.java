package org.tango.webapp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class maintains it is own cache of the entries and 
 *
 * @author Igor Khokhriakov <igor.khokhriakov@hzg.de>
 * @since 5/14/18
 */
public class UserContextCache implements UserContextStorage {
    private final Logger logger = LoggerFactory.getLogger(UserContextCache.class);
    
    private final UserContextStorage delegate;
    private final ExecutorService exec = Executors.newCachedThreadPool();

    private final CacheLoader<String, String> cacheLoader = new CacheLoader<String, String>() {
        @Override
        public String load(String key) throws Exception {
            return delegate.load(key);
        }
    };
    private final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1000L)
            .expireAfterAccess(30L, TimeUnit.MINUTES)
            .recordStats()
            .build(cacheLoader);


    public UserContextCache(UserContextStorage delegate) {
        this.delegate = delegate;
    }

    @Override
    public void save(String userName, String data) {
        cache.put(userName, data);
        exec.submit(() -> {
            try {
                delegate.save(userName, data);
            } catch (IOException e) {
                logger.error("Failed to save data for user[{}] due to {}", userName, e.getMessage());
                cache.invalidate(userName);
            }
        });
    }

    @Override
    public void delete(final String userName) {
        cache.invalidate(userName);

        exec.submit(() -> {
            try {
                delegate.delete(userName);
            } catch (IOException e) {
                logger.error("Failed to delete data for user[{}] due to {}", userName, e.getMessage());
            }
        });
    }

    @Override
    public String load(String userName) {
        try {
            return cache.get(userName);
        } catch (ExecutionException e) {
            logger.error("Failed to get data for user[{}]. Returning null...", userName);
            return null;
        }
    }
}
