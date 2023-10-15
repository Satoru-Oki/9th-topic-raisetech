package com.raisetech.api.sean.controller;

import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.service.RugbyPlayerInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class RugbyPlayerController {


    private final RugbyPlayerInfoService rugbyPlayerInfoService;

    public RugbyPlayerController(RugbyPlayerInfoService rugbyPlayerInfoService) {
        this.rugbyPlayerInfoService = rugbyPlayerInfoService;
    }
    //全件取得およびポジションでのデータ取得はRugbyPlayerを、体重と身長でのデータ取得はPlayerResponseを返す
    @GetMapping("/rugbyPlayers")
    public ResponseEntity<?> getRugbyPlayers(@RequestParam(required = false) Integer height, Integer weight, String posi) {
        List<RugbyPlayer> players = rugbyPlayerInfoService.findPlayersByReference(height, weight, posi);

        if (height != null || weight != null) {
            List<PlayerResponse> playerResponses = players.stream()
                    .map(PlayerResponse::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(playerResponses, HttpStatus.OK);
        }
        return new ResponseEntity<>(players, HttpStatus.OK);
    }
}
