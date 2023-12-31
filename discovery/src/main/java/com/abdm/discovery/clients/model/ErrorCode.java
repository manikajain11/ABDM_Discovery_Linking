package com.abdm.discovery.clients.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ErrorCode {
    UNKNOWN_ERROR_OCCURRED(2500),
    INVALID_TOKEN(2401),
    SERVICE_DOWN(2503),
    DB_OPERATION_FAILED(2504),
    TOO_MANY_REQUESTS_FOUND(2429),
    INVALID_BRIDGE_REGISTRY_REQUEST(2505),
    INVALID_BRIDGE_SERVICE_REQUEST(2506),
    INVALID_CM_SERVICE_REQUEST(2507),
    INVALID_CM_ENTRY(2508);

    private final int value;

    ErrorCode(int val) {
        value = val;
    }

    // Adding @JsonValue annotation that tells the 'value' to be of integer type while de-serializing.
    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static ErrorCode getNameByValue(int value) {
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.value == value)
                .findAny()
                .orElse(ErrorCode.UNKNOWN_ERROR_OCCURRED);
    }
}