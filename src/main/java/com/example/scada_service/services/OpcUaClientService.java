package com.example.scada_service.services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class OpcUaClientService {


    private OpcUaClient opcUaClient;

    @Value("${app.opc-server-address}")
    private String endpointUrl;


    public OpcUaClientService(OpcUaClient opcUaClient) {
        this.opcUaClient = opcUaClient;
    }

    @PostConstruct
    public void initializeClient() throws Exception {
        opcUaClient = OpcUaClient.create(endpointUrl);
        opcUaClient.connect().get();
        log.info("OPC UA клиент подключен к серверу: " + endpointUrl);
    }

    @PreDestroy
    public void shutdownClient() throws Exception {
        if (opcUaClient != null) {
            opcUaClient.disconnect().get();
            log.info("OPC UA клиент отключен.");
        }
    }

    public CompletableFuture<DataValue> readTagValue(String nodeIdString) {

        try {
            // Создание NodeId для идентификатора узла
            NodeId nodeId = new NodeId(2, nodeIdString); // Замените 2 на нужный namespace

            // Чтение значения с узла по NodeId
            return opcUaClient.readValue(0, TimestampsToReturn.Both, nodeId);
        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.failedFuture(e);
        }
    }




    private void onSubscriptionValue(UaMonitoredItem item, DataValue value) {
        log.info(
                "subscription value received: item={}, value={}",
                item.getReadValueId().getNodeId(), value.getValue());
    }



}
