package com.example.scada_service.services;

import com.example.scada_service.configuration.OpcUaConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpcUaSubscriptionService {

    private final OpcUaConnectionManager connectionManager;
    private final OpcUaSubscriptionManager subscriptionManager;
    private final DataProcessingService dataProcessingService;
    private final OpcUaConfig opcUaConfig;

    @PostConstruct
    public void init() throws Exception {
        startMonitoring();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            log.info("Приложение всё ещё работает, данные обновляются...");
        }, 0, 10, TimeUnit.SECONDS);
    }

    public void startMonitoring() throws Exception {
        List<String> tags = opcUaConfig.getTags();

        UaSubscription subscription = connectionManager.getActiveSession() != null
                ? subscriptionManager.createSubscription()
                : null;

        if (subscription != null) {
            List<UaMonitoredItem> items = subscriptionManager.createMonitoredItems(subscription, tags);
            items.forEach(item -> {
                if (item.getStatusCode().isGood()) {
                    item.setValueConsumer(dataProcessingService::handleDataChange);
                    log.info("Подписка успешно создана для узла: {}", item.getReadValueId().getNodeId());
                } else {
                    log.warn("Ошибка создания подписки для узла: {}", item.getReadValueId().getNodeId());
                }
            });
        } else {
            log.error("Подписка не была создана.");
        }
    }
}
