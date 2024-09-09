package com.example.scada_service.configuration;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpcClientConfig {

    @Value("${app.opc-server-address}")
    private String endpointUrl;

    @Bean
    public OpcUaClient opcUaClient() throws UaException {
        return OpcUaClient.create(endpointUrl);
    }
}
