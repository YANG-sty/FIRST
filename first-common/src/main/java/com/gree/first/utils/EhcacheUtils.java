package com.gree.first.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.List;

/**
 * eache 缓存工具类
 *
 * @author yangLongFei 2020-11-23-9:38
 */
public class EhcacheUtils {

    private static CacheManager cacheManager = CacheManager.getInstance();
    public static final String SMSCODE = "smsCode";
    public static final String CODE_INVOKE_TIMES = "codeInvokeTimes";
    public static final String CONSTANT = "CONSTANT";


    /**
     * 获取缓存
     * @param cacheName 缓存名称
     * @param key 要查询的key
     * @return
     */
    public static Object get(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        Element element = cache.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    /**
     * 清除某个缓存中 key 的数据
     * @param cacheName 缓存名称
     * @param key 需要删除的key
     * @return
     */
    public static boolean clear(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        boolean remove = cache.remove(key);
        return remove;
    }

    /**
     * 获得该缓存中的 全部关键字 keys
     * @param cacheName 缓存名称
     * @return
     */
    public static List getAllKeys(String cacheName) {
        List keys = cacheManager.getCache(cacheName).getKeys();
        return keys;
    }

    /**
     * 添加缓存
     * @param cacheName 缓存名称
     * @param key
     * @param value
     */
    public static void put(String cacheName, String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        Element element = new Element(key, value);
        cache.put(element);
    }


    /**
     * 添加缓存数据，
     * @param cacheName 缓存名称
     * @param key
     * @param value
     * @param timeToIdelSeconds 到期时间
     * @param timeToLiveSeconds 存活时间
     */
    public static void put(String cacheName, String key, Object value, int timeToIdelSeconds, int timeToLiveSeconds) {
        Cache cache = cacheManager.getCache(cacheName);
        Element element = new Element(key, value);
        //数据默认永不过期，数值非常大
        if (timeToIdelSeconds > 0) {
            element.setTimeToIdle(timeToIdelSeconds);
        }
        //数据默认永不过期，数值非常大
        if (timeToLiveSeconds > 0) {
            element.setTimeToLive(timeToLiveSeconds);
        }
        cache.put(element);
    }

}
