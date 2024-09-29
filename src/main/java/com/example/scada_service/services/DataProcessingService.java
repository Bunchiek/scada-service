package com.example.scada_service.services;

import com.example.scada_service.model.OpcUaData;
import com.example.scada_service.repository.OpcUaDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataProcessingService {

    private final OpcUaDataRepository opcUaDataRepository;

    public void handleDataChange(UaMonitoredItem item, DataValue value) {
        Variant variant = value.getValue();
        String tagName = item.getReadValueId().getNodeId().getIdentifier().toString();
        Long tagValue = ((Number) variant.getValue()).longValue();
        LocalDateTime timestamp = LocalDateTime.now();

        OpcUaData data = new OpcUaData(tagName, tagValue, timestamp);
        opcUaDataRepository.save(data);
        log.info("Новое значение тега {}", data);
    }
}
