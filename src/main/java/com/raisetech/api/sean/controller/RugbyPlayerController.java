package com.raisetech.api.sean.controller;

import com.raisetech.api.sean.service.RugbyPlayerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class RugbyPlayerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    RugbyPlayerInfoService rugbyPlayerInfoService;

    @GetMapping("/rugbyPlayers")
    public ResponseEntity<?> getRugbyPlayers(@RequestParam(required = false) Integer height, Integer weight, String rugbyPosition) {
        return rugbyPlayerInfoService.findPlayersByReference(height, weight, rugbyPosition);
    }
}
