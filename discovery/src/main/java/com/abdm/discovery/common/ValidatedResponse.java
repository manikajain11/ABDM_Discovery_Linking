package com.abdm.discovery.common;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidatedResponse {
    String id;
    String callerRequestId;
    JsonNode deSerializedJsonNode;
}