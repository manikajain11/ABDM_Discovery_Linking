package com.abdm.discovery.session;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.abdm.discovery.common.model.GrantType;
import lombok.Builder;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionRequest {
    String clientId;
    String clientSecret;
    String refreshToken;
    GrantType grantType;
}