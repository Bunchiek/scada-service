package com.example.scada_service.services;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

import java.util.concurrent.ExecutionException;

public class OpcUaTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try {
            // Подключение к серверу OPC UA
            OpcUaClient client = OpcUaClient.create("opc.tcp://127.0.0.1:49320");
            // Укажи правильный адрес сервера
            client.connect().get();  // Подключение к серверу

            OpcUaClientService opcUaClientService = new OpcUaClientService(client);

            // Чтение значения тега
            String nodeId = "Simulation Examples.Functions.Random2";  // Замените на реальный NodeId
            DataValue dataValue = opcUaClientService.readTagValue(nodeId).get();// Получаем значение

            // Вывод значения в консоль
            System.out.println("Значение узла " + nodeId + ": " + dataValue.getValue().getValue());

            // Отключение клиента после завершения работы
            client.disconnect().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
