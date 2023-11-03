package com.raisetech.api.sean.controller;

import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.form.PlayerCreateForm;
import com.raisetech.api.sean.form.PlayerUpdateForm;
import com.raisetech.api.sean.service.RugbyPlayerInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;


@RestController
public class RugbyPlayerController {

    private final RugbyPlayerInfoService rugbyPlayerInfoService;

    public RugbyPlayerController(RugbyPlayerInfoService rugbyPlayerInfoService) {
        this.rugbyPlayerInfoService = rugbyPlayerInfoService;
    }

    @GetMapping("/rugbyPlayers")
    public ResponseEntity<List<PlayerDataResponse>> getRugbyPlayers(@RequestParam(required = false) Integer height, Integer weight, String rugby_position) {
        List<PlayerDataResponse> players = rugbyPlayerInfoService.findPlayersByReference(height, weight, rugby_position);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @PostMapping("/rugbyPlayers")
    public ResponseEntity<Map<String, String>> createRugbyPlayer(@RequestBody @Validated PlayerCreateForm playerCreateForm, UriComponentsBuilder uriBuilder) {
        RugbyPlayer rugbyPlayer = rugbyPlayerInfoService.insertRugbyPlayers(playerCreateForm);
        URI location = uriBuilder.path("/rugbyPlayer/{id}").buildAndExpand(rugbyPlayer.getId()).toUri();
        return ResponseEntity.created(location).body(Map.of("message", "選手が登録されました"));
    }

    @PatchMapping("/rugbyPlayers/{id}")
    public ResponseEntity<Map<String, String>> updateRugbyPlayer(@PathVariable("id") String id, @RequestBody @Validated PlayerUpdateForm playerUpdateForm) {
        rugbyPlayerInfoService.updateRugbyPlayer(id, playerUpdateForm.getName(), playerUpdateForm.getHeight(), playerUpdateForm.getWeight(), playerUpdateForm.getRugby_position());
        return ResponseEntity.ok(Map.of("message", "選手データが更新されました"));
    }

    @DeleteMapping("rugbyPlayers/{id}")
    public ResponseEntity<Map<String, String>> deleteRugbyPlayer(@PathVariable("id") String id) {
        rugbyPlayerInfoService.deleteRugbyPlayer(id);
        return ResponseEntity.ok(Map.of("message", "選手が消去されました"));
    }
}
