package com.uzum.jfinesandpenalties.config;

import com.uzum.jfinesandpenalties.config.props.RedisProps;
import com.uzum.jfinesandpenalties.constant.Constant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;


@EnableCaching
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisConfiguration {

    RedisProps redisProps;

    @Bean
    RedisSerializer<Object> redisJsonSerializer() {
        return RedisSerializer.json();
    }


    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        var configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisProps.getHost());
        configuration.setPort(redisProps.getPort());
        configuration.setPassword(redisProps.getPassword());
        configuration.setDatabase(redisProps.getDatabase());

        var lettuceClientConfiguration = LettuceClientConfiguration
                .builder()
                .commandTimeout(Duration.ofMillis(redisProps.getTimeout()))
                .shutdownTimeout(Duration.ofMillis(redisProps.getShutdownTimeout()))
                .build();

        return new LettuceConnectionFactory(configuration, lettuceClientConfiguration);
    }


    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory, RedisSerializer<Object> redisJsonSerializer) {
        var defaultConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMillis(redisProps.getDefaultTtl()))
                .disableCachingNullValues()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(redisJsonSerializer));

        Map<String, RedisCacheConfiguration> perCacheTtl = Map.of(
                Constant.FINES_REDIS_KEYS, defaultConfiguration.entryTtl(Duration.ofMillis(redisProps.getFinesTtl())),
                Constant.OFFICER_REDIS_KEYS, defaultConfiguration.entryTtl(Duration.ofMillis(redisProps.getOfficerTtl()))
        );

        return RedisCacheManager
                .builder(factory)
                .cacheDefaults(defaultConfiguration)
                .withInitialCacheConfigurations(perCacheTtl)
                .transactionAware()
                .build();
    }
}

