package com.raisetech.api.sean.controller;

import com.raisetech.api.sean.service.RugbyPlayerInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RugbyPlayerController {

    private final RugbyPlayerInfoService rugbyPlayerInfoService;

    public RugbyPlayerController(RugbyPlayerInfoService rugbyPlayerInfoService) {
        this.rugbyPlayerInfoService = rugbyPlayerInfoService;
    }

    @GetMapping("/rugbyPlayers")
    public ResponseEntity<?> getRugbyPlayers(@RequestParam(required = false) Integer height, Integer weight, String rugbyPosition) {
        return rugbyPlayerInfoService.findPlayersByReference(height, weight, rugbyPosition);
    }
}
