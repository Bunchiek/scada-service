package com.example.scada_service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.OpcUaSession;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpcUaConnectionManager {

    private final OpcUaClient opcUaClient;
    private static final int RETRY_COUNT = 3;

    public OpcUaSession getActiveSession() throws ExecutionException, InterruptedException {
        for (int i = 0; i < RETRY_COUNT; i++) {
            try {
                CompletableFuture<OpcUaSession> sessionFuture = opcUaClient.getSession();
                if (sessionFuture.isDone()) {
                    OpcUaSession session = sessionFuture.get();
                    log.info("Сессия активна: {}", session.getSessionId());
                    return session;
                } else {
                    log.warn("Сессия не активна. Попытка переподключения...");
                    opcUaClient.connect().get();
                }
            } catch (Exception e) {
                log.error("Ошибка при получении сессии или переподключении: {}", e.getMessage());
                if (i == RETRY_COUNT - 1) {
                    throw e;
                }
            }
        }
        throw new NullPointerException("Сессия не была установлена после всех попыток.");
    }
}
