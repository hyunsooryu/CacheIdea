package com.redis.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * ActionData용 RedisTemplate
 */


public class KBankRedisTemplate implements RedisTemplate<String, ActionData>{

    private final ApplicationContext applicationContext;

    @Autowired
    KBankRedisTemplate(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public String set(String key, ActionData value) throws JsonProcessingException {
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
        String val = objectMapper.writeValueAsString(value);


        StatefulRedisClusterConnection<String,String> conn =  applicationContext.getBean(
                StatefulRedisClusterConnection.class);
        System.out.println("================================");
        System.out.println(conn.toString());
        System.out.println("================================");
        RedisClusterCommands<String,String> syncCommands = conn.sync();
        System.out.println("================================");
        System.out.println(syncCommands.toString());
        System.out.println("================================");
        return syncCommands.set(key, val);
    }

    public ActionData get(String key) throws JsonProcessingException {
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
        StatefulRedisClusterConnection<String,String> conn =  applicationContext.getBean(
                StatefulRedisClusterConnection.class);
        System.out.println("================================");
        System.out.println(conn.toString());
        System.out.println("================================");
        RedisClusterCommands<String,String> syncCommands = conn.sync();
        System.out.println("================================");
        System.out.println(syncCommands.toString());
        System.out.println("================================");
        String val = syncCommands.get(key);
        if(!StringUtils.hasLength(val)){
            ActionData actionData = new ActionData();
            actionData.put("cache", "miss");
            return actionData;
        }
        ActionData actionData = objectMapper.readValue(val, ActionData.class);
        return actionData;
    }

}
