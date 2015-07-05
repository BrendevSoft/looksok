package com.looksok.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jacek Milewski (looksok.wordpress.com)
 */
public class CacheDemo {

    LoadingCache<String, String> cache;
    private DataDao dataDao;

    public CacheDemo(DataDao dataDao) {
        this.dataDao = dataDao;
    }

    public void initCache() {
        cache = CacheBuilder.newBuilder()
//                .expireAfterAccess(1, TimeUnit.MICROSECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return dataDao.getValueForKey(key);
                    }
                });
    }

    public String getValue(String key) {
        try {
            String value = cache.get(key);
            System.out.println("getting value from cache for '" + key + "': " + value);
            return value;
        } catch (ExecutionException e) {
            System.out.println("Exception getting value for key '" + key + "': " + e.getMessage());
            return null;
        }
    }
}
