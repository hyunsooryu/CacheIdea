package com.redis.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cache")
public class CacheApi {
    public CacheApi(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
    private final ApplicationContext applicationContext;
    @GetMapping("/{key}")
    public ActionData get(@PathVariable String key) throws JsonProcessingException {
        KBankRedisTemplate redisTemplate = applicationContext.getBean(KBankRedisTemplate.class);
        return redisTemplate.get(key);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String set(@RequestBody ActionData actionData) throws JsonProcessingException {
        KBankRedisTemplate redisTemplate = applicationContext.getBean(KBankRedisTemplate.class);
        return redisTemplate.set((String)actionData.get("key"), actionData);
    }

}
