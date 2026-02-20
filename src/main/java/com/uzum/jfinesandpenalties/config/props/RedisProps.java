package com.uzum.jfinesandpenalties.config.props;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "data.redis")
public class RedisProps {
    String host;
    int port;

    int database;
    String username;
    String password;

    int defaultTtl;

    int timeout;
    int shutdownTimeout;

    int finesTtl;
    int officerTtl;
}
