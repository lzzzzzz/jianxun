package org.openmore.cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.openmore.cms.dto.api.DictionaryDto;
import org.openmore.cms.service.CacheService;
import org.openmore.common.utils.RedisOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CacheServiceImpl extends BaseServiceImpl implements CacheService {
    @Autowired
    private RedisOps redisOps;

    @Override
    public String get(String key) {
        return redisOps.get(key);
    }

    @Override
    public void set(String key, String data, long expireSecond) {
        redisOps.set(key, data, expireSecond);
    }

    @Override
    public void delete(String key) {
        redisOps.del(key);
    }

    @Override
    public <T> List<T> get(String key, Class<T> clazz) {
        String jsonObj = this.get(key);
        if(jsonObj != null) {
            try{
                List<T> resultList = JSON.parseArray(jsonObj, clazz);
                if(resultList != null) {
                    return resultList;
                }
            }catch (Exception e) {

            }
        }
        return null;
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        String jsonStr = this.get(key);
        if(!StringUtils.isEmpty(jsonStr)) {
            try{
                T result = JSON.parseObject(jsonStr, clazz);
                if(result != null) {
                    return result;
                }
            }catch (Exception e) {

            }
        }
        return null;
    }

    @Override
    public <T> void set(String key, T data, long expireSecond) {
        try{
            // 默认2小时
            this.set(key, JSON.toJSONString(data), 7200);
        }catch (Exception e){}
    }
}