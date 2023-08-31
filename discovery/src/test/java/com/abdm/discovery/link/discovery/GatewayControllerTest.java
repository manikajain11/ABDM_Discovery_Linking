// package com.abdm.discovery.link.discovery;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.nimbusds.jose.jwk.JWKSet;
// import com.abdm.discovery.clients.DiscoveryServiceClient;
// import com.abdm.discovery.common.Authenticator;
// import com.abdm.discovery.common.Constants;
// import com.abdm.discovery.common.RequestOrchestrator;
// import com.abdm.discovery.common.ResponseOrchestrator;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Captor;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.web.reactive.server.WebTestClient;
// import reactor.core.publisher.Mono;

// import java.util.List;

// import static com.abdm.discovery.common.Constants.X_CM_ID;
// import static com.abdm.discovery.common.Constants.X_HIP_ID;
// import static com.abdm.discovery.common.Role.CM;
// import static com.abdm.discovery.common.Role.HIP;
// import static com.abdm.discovery.testcommon.TestBuilders.caller;
// import static com.abdm.discovery.testcommon.TestBuilders.string;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.when;
// import static org.springframework.http.HttpHeaders.AUTHORIZATION;
// import static org.springframework.http.MediaType.APPLICATION_JSON;
// import static reactor.core.publisher.Mono.empty;
// import static reactor.core.publisher.Mono.just;

// @ExtendWith(SpringExtension.class)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @AutoConfigureWebTestClient
// class DiscoveryControllerTest {
//     @MockBean
//     RequestOrchestrator<DiscoveryServiceClient> requestOrchestrator;

//     @Qualifier("discoveryResponseOrchestrator")
//     @MockBean
//     ResponseOrchestrator discoveryResponseOrchestrator;

//     @Autowired
//     WebTestClient webTestClient;

//     @MockBean(name = "centralRegistryJWKSet")
//     JWKSet centralRegistryJWKSet;

//     @Captor
//     ArgumentCaptor<JsonNode> jsonNodeArgumentCaptor;

//     @MockBean
//     Authenticator authenticator;

//     @Test
//     void shouldFireAndForgetForOnDiscover() {
//         var token = string();
//         when(authenticator.verify(token)).thenReturn(just(caller().roles(List.of(HIP)).build()));
//         when(discoveryResponseOrchestrator.processResponse(any(), eq(X_CM_ID))).thenReturn(Mono.empty());

//         webTestClient
//                 .post()
//                 .uri(Constants.PATH_CARE_CONTEXTS_ON_DISCOVER)
//                 .contentType(APPLICATION_JSON)
//                 .header(AUTHORIZATION, token)
//                 .bodyValue("{}")
//                 .exchange()
//                 .expectStatus()
//                 .isAccepted();
//     }
// }