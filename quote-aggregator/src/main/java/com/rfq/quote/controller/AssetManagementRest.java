package com.rfq.quote.controller;

import com.rfq.quote.service.KafkaService;
import com.rfq.common.enums.Actions;
import com.rfq.common.requests.AssetManagementRequest;
import com.rfq.common.requests.EnclaveServiceRequest;
import com.rfq.common.responses.AssetManagementResponse;
import com.rfq.common.responses.EnclaveServiceResponse;
import com.rfq.enclave.host.NitroEnclaveClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AssetManagementRest {

    private final NitroEnclaveClient client;
    private final KafkaService kafkaService;

    public AssetManagementRest(NitroEnclaveClient client, KafkaService kafkaService) {
        this.client = client;
        this.kafkaService = kafkaService;
    }

    @GetMapping("/ping")
    public String healthCheck() {
        return "200 Ok";
    }

    @PostMapping("/echo")
    public String sendEchoRequest(@RequestBody AssetManagementRequest assetManagementRequest){
        EnclaveServiceRequest<AssetManagementRequest> request = new EnclaveServiceRequest<>();
        request.setData(assetManagementRequest);
        request.setAction(Actions.ECHO.name());
        EnclaveServiceResponse<AssetManagementResponse> response = client.send(request);
        if (response.getIsError()) { // Handle the response
            String errorMessage = String.format("Something went wrong: %s", response.getError());
            return "error " + errorMessage;
        } else {
            String resultValue = response.getData().getValue();
            return "value " +  resultValue + " executionTimeMs " + response.getDuration();
        }
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishMessage(@RequestParam("message") String message) {
        kafkaService.sendMessage(message);
        return ResponseEntity.ok("Message sent to Kafka: " + message);
    }
}
