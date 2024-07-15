package com.redis.common;

public interface RedisTemplate<K, V>{
    public String set(K k, V v) throws Exception;

    public V get(K k) throws Exception;
}
