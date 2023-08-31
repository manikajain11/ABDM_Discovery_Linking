//package com.abdm.discovery.link.discovery;
//
//import com.abdm.discovery.common.RequestOrchestrator;
//import com.abdm.discovery.clients.DiscoveryServiceClient;
//import com.abdm.discovery.common.cache.CacheAdapter;
//import com.abdm.discovery.common.RedundantRequestValidator;
//import com.abdm.discovery.common.Validator;
//import com.abdm.discovery.common.ValidatedRequestAction;
//import com.abdm.discovery.clients.ClientError;
//import com.abdm.discovery.common.ValidatedRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Bean
//@Configuration
//public class DiscoveryConfig {
//    public RequestOrchestrator<DiscoveryServiceClient> discoveryRequestOrchestrator(
//            CacheAdapter<String, String> requestIdMappings,
//            RedundantRequestValidator redundantRequestValidator,
//            Validator validator,
//            DiscoveryServiceClient serviceClient,
//            ValidatedRequestAction requestAction) {
//        return new RequestOrchestrator<>(requestIdMappings, redundantRequestValidator, validator,
//                serviceClient, requestAction);
//    }
//
//    // Define other beans or configuration if needed
//}
////
////
////
//////import com.abdm.discovery.clients.DiscoveryServiceClient;
//////import com.abdm.discovery.common.RequestOrchestrator;
//////import org.springframework.context.annotation.Bean;
//////import org.springframework.context.annotation.Configuration;
//////import org.springframework.web.client.RestTemplate;
//////
//////@Configuration
//////public class DiscoveryConfig {
//////
//////    @Bean
//////    public RequestOrchestrator<DiscoveryServiceClient> discoveryRequestOrchestrator() {
//////        // Define the creation and configuration of your RequestOrchestrator<DiscoveryServiceClient> bean
//////        // You might need to inject necessary dependencies here
//////        return new RequestOrchestrator<>(); // Example instantiation, replace with your actual implementation
//////    }
//////
//////}
////
