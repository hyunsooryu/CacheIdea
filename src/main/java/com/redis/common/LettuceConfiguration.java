package com.redis.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Configuration
public class LettuceConfiguration {

    private final ApplicationContext applicationContext;

    @Autowired
    public LettuceConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Bean
    List<RedisURI> redisNodes(){
        return Arrays.asList(
                RedisURI.create("redis://127.0.0.1:6300"),
                RedisURI.create("redis://127.0.0.1:6301"),
                RedisURI.create("redis://127.0.0.1:6302"),
                RedisURI.create("redis://127.0.0.1:6400"),
                RedisURI.create("redis://127.0.0.1:6401"),
                RedisURI.create("redis://127.0.0.1:6402")
        );
    }


    @Bean
    public RedisClusterClient redisClusterClient(List<RedisURI> redisNodes){
        ClientResources resources = DefaultClientResources.builder().build();

        RedisClusterClient client =
                RedisClusterClient.create(resources, redisNodes);

        ClusterTopologyRefreshOptions refreshOptions = ClusterTopologyRefreshOptions
                .builder().enablePeriodicRefresh().refreshPeriod(Duration.ofSeconds(30)).build();

        client.setOptions(ClusterClientOptions.builder().topologyRefreshOptions(refreshOptions).build());

        return client;
    }

    @Bean
    public StatefulRedisClusterConnection<String,String> statefulRedisClusterConnection(RedisClusterClient redisClusterClient){
        return redisClusterClient.connect();
    }

    @Bean
    public ObjectMapper mapper(){
        return new ObjectMapper();
    }




}
