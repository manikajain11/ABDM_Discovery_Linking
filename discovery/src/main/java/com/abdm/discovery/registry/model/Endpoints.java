package com.abdm.discovery.registry.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Endpoints {
    List<EndpointDetails> hipEndpoints;
    List<EndpointDetails> hiuEndpoints;
    List<EndpointDetails> healthLockerEndpoints;
}