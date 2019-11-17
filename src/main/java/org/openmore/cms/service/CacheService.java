package org.openmore.cms.service;

import com.alibaba.fastjson.JSON;
import org.openmore.cms.dto.api.DictionaryDto;

import java.util.List;

public interface  CacheService {
    /**
     * 获得缓存数据
     * @param key
     * @return
     */
    String get(String key);


    /**
     * 获得缓存数据
     * @param key
     * @return
     */
    <T> List<T> get(String key, Class<T> clazz);

    /**
     * 获得指定类型的对象
     * @param key
     * @param <T>
     * @return
     */
    <T> T getObject(String key, Class<T> clazz);

    /**
     * 设置缓存 expireSecond为负数表示不过期
     * @param key
     * @param data
     * @param expireSecond
     */
    void set(String key, String data, long expireSecond);


    /**
     * 设置缓存 expireSecond为负数表示不过期
     * @param key
     * @param data
     * @param expireSecond
     */
    <T> void set(String key, T data, long expireSecond);

    /**
     * 删除缓存数据
     * @return
     */
    void delete(String key);
}