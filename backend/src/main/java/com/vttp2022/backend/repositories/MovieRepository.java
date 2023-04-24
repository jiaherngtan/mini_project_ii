package com.vttp2022.backend.repositories;

import java.io.StringReader;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.vttp2022.backend.configs.RedisConfig;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class MovieRepository {

    @Autowired
    @Qualifier(RedisConfig.CACHE_MOVIE)
    private RedisTemplate<String, String> redisTemplate;

    public boolean checkFromRedis(String key) {
        return redisTemplate.hasKey(key);
    }

    public Optional<JsonArray> getArrFromRedis(String key) {
        String value = redisTemplate.opsForValue().get(key);
        if (null == value)
            return Optional.empty();
        JsonReader jr = Json.createReader(new StringReader(value));
        JsonArray ja = jr.readArray();
        return Optional.of(ja);
    }

    public Optional<JsonObject> getObjFromRedis(String key) {
        String value = redisTemplate.opsForValue().get(key);
        if (null == value)
            return Optional.empty();
        JsonReader jr = Json.createReader(new StringReader(value));
        JsonObject jo = jr.readObject();
        return Optional.of(jo);
    }

    public void addToRedis(String key, JsonArray value) {
        redisTemplate.opsForValue().set(key, value.toString());
        redisTemplate.expire(key, 300, TimeUnit.HOURS);
    }

    public void addToRedis(String key, JsonObject value) {
        redisTemplate.opsForValue().set(key, value.toString());
        redisTemplate.expire(key, 300, TimeUnit.HOURS);
    }

}
