package com.abdm.discovery.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

import static java.lang.String.format;

@Configuration
@ConfigurationProperties(prefix = "identity")
@AllArgsConstructor
@Getter
public class IdentityProperties {
    private String url;
    private String clientSecret;
    private String clientId;
    private String host;
    private String realm;
    private String userName;
    private String password;
    private int accessTokenExpiryInMinutes;

    public IdentityProperties() {
        // Default no-arg constructor required for @ConfigurationProperties
    }
    public String getJwkUrl() {
        return format("%s/realms/%s/protocol/openid-connect/certs", url, realm);
    }
}