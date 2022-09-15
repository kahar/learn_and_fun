package io.github.kahar.twodb.shop.third;


import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
public class ThirdDbConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.third-datasource")
    public MongodConfig thirdDataSource(@Value("${spring.data.mongodb.port}") int port) throws UnknownHostException {
        return MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();
    }
}
