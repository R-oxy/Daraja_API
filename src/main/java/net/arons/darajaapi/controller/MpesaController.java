package net.arons.darajaapi.controller;

import net.arons.darajaapi.dtos.*;
import net.arons.darajaapi.services.DarajaApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mobile-money")
public class MpesaController {

    private final DarajaApiService darajaApiService;
    private final AcknowledgeResponse acknowledgeResponse;

    public MpesaController(DarajaApiService darajaApiService, AcknowledgeResponse acknowledgeResponse) {
        this.darajaApiService = darajaApiService;
        this.acknowledgeResponse = acknowledgeResponse;
    }

    @GetMapping(path = "/token", produces = "application/json")
    public ResponseEntity<AccessTokenResponse> getAccessToken(){
        return ResponseEntity.ok(darajaApiService.getAccessToken());
    }

    @PostMapping(path = "/register-url", produces = "application/json")
    public ResponseEntity<RegisterUrlResponse> registerUrl(){
        return ResponseEntity.ok(darajaApiService.registerUrl());
    }

    @PostMapping(path = "/validation", produces = "application/json")
    public ResponseEntity<AcknowledgeResponse> validateTransaction(@RequestBody TransactionResults transactionResults){
        return ResponseEntity.ok(acknowledgeResponse);
    }

    @PostMapping(path = "/simulate-c2b", produces = "application/json")
    public ResponseEntity<SimulateC2BResponse> simulateC2BTransaction(@RequestBody SimulateC2BRequest simulateC2BRequest){
        return ResponseEntity.ok(darajaApiService.simulateC2BTransaction(simulateC2BRequest));
    }
}
