package com.vttp2022.backend.configs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    public static final String CACHE_MOVIE = "movie-cache";

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    @Value("${spring.redis.user}")
    private String redisUser;

    private static String redisPassword = System.getenv("REDIS_PASSWORD");

    @Bean(CACHE_MOVIE)
    public RedisTemplate<String, String> redisTemplate() {

        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        config.setDatabase(redisDatabase);
        config.setUsername(redisUser);
        config.setPassword(redisPassword);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config,
                jedisClient);
        jedisFac.afterPropertiesSet();
        System.out.println(">>> redis setup...");
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(template.getKeySerializer());
        template.setHashKeySerializer(template.getValueSerializer());

        return template;
    }

}
