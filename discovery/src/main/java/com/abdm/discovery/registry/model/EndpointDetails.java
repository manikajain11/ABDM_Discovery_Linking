package com.abdm.discovery.registry.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EndpointDetails {
    EndpointUse use;
    EndpointConnectionType connectionType;
    String address;
}