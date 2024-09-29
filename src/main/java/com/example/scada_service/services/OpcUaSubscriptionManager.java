package com.example.scada_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.*;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.structured.*;
import org.eclipse.milo.opcua.stack.core.types.enumerated.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpcUaSubscriptionManager {

    private final OpcUaClient opcUaClient;

    public UaSubscription createSubscription() throws ExecutionException, InterruptedException {
        return opcUaClient.getSubscriptionManager().createSubscription(1000.0).get();
    }

    public List<UaMonitoredItem> createMonitoredItems(UaSubscription subscription, List<String> nodeIdStrings) throws ExecutionException, InterruptedException {
        List<MonitoredItemCreateRequest> requests = new ArrayList<>();

        for (String nodeIdString : nodeIdStrings) {
            NodeId nodeId = new NodeId(2, nodeIdString);
            UInteger clientHandle = subscription.nextClientHandle();

            MonitoringParameters parameters = new MonitoringParameters(
                    clientHandle, 1000.0, null, UInteger.valueOf(10), true
            );

            ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);
            MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                    readValueId, MonitoringMode.Reporting, parameters
            );

            requests.add(request);
        }

        return subscription.createMonitoredItems(TimestampsToReturn.Both, requests).get();
    }
}
