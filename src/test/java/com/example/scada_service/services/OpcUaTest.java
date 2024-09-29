package com.example.scada_service.services;

import org.eclipse.milo.opcua.sdk.client.AddressSpace;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.BuiltinReferenceType;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseDirection;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseResultMask;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseResult;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.ReferenceDescription;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

@EnableScheduling
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
