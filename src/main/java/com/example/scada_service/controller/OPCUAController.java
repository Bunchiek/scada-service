package com.example.scada_service.controller;

import com.example.scada_service.services.OpcUaClientService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ExecutionException;

@EnableScheduling
@RequiredArgsConstructor
@Controller
public class OPCUAController {

    private final OpcUaClientService opcUaClientService;

    @Scheduled(fixedRate = 3000)
    public void printer() throws ExecutionException, InterruptedException {

        DataValue dataValue = opcUaClientService.readTagValue("Simulation Examples.Functions.Random4").get();
        System.out.println(dataValue.getValue().getValue());


    }
}
