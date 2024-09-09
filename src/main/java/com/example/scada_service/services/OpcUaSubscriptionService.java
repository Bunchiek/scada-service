package com.example.scada_service.services;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.*;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.structured.*;
import org.eclipse.milo.opcua.stack.core.types.enumerated.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OpcUaSubscriptionService {

    private final OpcUaClient opcUaClient;

    public OpcUaSubscriptionService(OpcUaClient opcUaClient) {
        this.opcUaClient = opcUaClient;
    }

    // Метод для подписки на изменения значений тегов
    public void subscribeToTag(String nodeIdString) throws ExecutionException, InterruptedException {
        // Создание идентификатора узла (NodeId)
        NodeId nodeId = new NodeId(2, nodeIdString); // NamespaceIndex = 2, заменить на нужный

        // Создание подписки с периодом обновления 1000 мс (1 секунда)
        UaSubscription subscription = opcUaClient.getSubscriptionManager()
                .createSubscription(1000.0) // 1000 мс = 1 секунда
                .get();

        // Создание параметров для MonitoredItem
        UInteger clientHandle = subscription.nextClientHandle();
        MonitoringParameters parameters = new MonitoringParameters(
                clientHandle,
                1000.0,     // Интервал выборки 1000 мс
                null,       // Не используем диапазон для выборки
                UInteger.valueOf(10),   // Максимальное количество записей
                true        // Разрешить дискретные уведомления
        );

        // Создание запроса для MonitoredItem
        ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);
        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                readValueId,
                MonitoringMode.Reporting,
                parameters
        );

        // Создание MonitoredItem
        List<UaMonitoredItem> items = subscription.createMonitoredItems(
                TimestampsToReturn.Both,
                Collections.singletonList(request),
                (item, value) -> {
                    System.out.println("Изменение значения для узла " + nodeId + ": " + value.getValue().getValue());
                }
        ).get();

        // Проверяем статус создания MonitoredItem
        for (UaMonitoredItem item : items) {
            if (item.getStatusCode().isGood()) {
                System.out.println("Подписка успешно создана для узла: " + nodeIdString);
            } else {
                System.out.println("Ошибка создания подписки для узла: " + nodeIdString);
            }
        }
    }
}
