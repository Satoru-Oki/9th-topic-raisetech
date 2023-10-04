package com.raisetech.api.sean.controller;

import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.service.RugbyPlayerInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RugbyPlayerController {


    private final RugbyPlayerInfoService rugbyPlayerInfoService;

    public RugbyPlayerController(RugbyPlayerInfoService rugbyPlayerInfoService) {
        this.rugbyPlayerInfoService = rugbyPlayerInfoService;
    }

    @GetMapping("/rugbyPlayers/all")
    public List<RugbyPlayer> getRugbyPlayers() {
        return rugbyPlayerInfoService.findAll();
    }

    @GetMapping("/rugbyPlayers")
    public List<PlayerResponse> getRugbyPlayersHeight(@RequestParam int height) {
        List<RugbyPlayer> names = rugbyPlayerInfoService.findByHeightHigherThan(height);
        List<PlayerResponse> response = names.stream().map(name -> new PlayerResponse(name)).toList();
        return response;
    }
}
