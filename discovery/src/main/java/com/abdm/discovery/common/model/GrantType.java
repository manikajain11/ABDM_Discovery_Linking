package com.abdm.discovery.common.model;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.stereotype.Component;

@Component
public enum GrantType {
    CLIENT_CREDENTIALS("client_credentials"),
    REFRESH_TOKEN("refresh_token"),
    NONE("");

    private final String grantType;

    GrantType(String value) {
        grantType = value;
    }

    @JsonValue
    public String getValue() {
        return grantType;
    }
}