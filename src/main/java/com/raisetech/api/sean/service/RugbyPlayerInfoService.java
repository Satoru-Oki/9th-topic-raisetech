package com.raisetech.api.sean.service;

import com.raisetech.api.sean.controller.PlayerDataResponse;
import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.form.PlayerCreateForm;
import com.raisetech.api.sean.mapper.RugbyPlayerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RugbyPlayerInfoService {

    private final RugbyPlayerMapper rugbyPlayerMapper;

    public RugbyPlayerInfoService(RugbyPlayerMapper rugbyPlayerMapper) {
        this.rugbyPlayerMapper = rugbyPlayerMapper;
    }

    public void insertRugbyPlayers(List<RugbyPlayer> rugbyPlayersList) {
        for (RugbyPlayer rugbyPlayer : rugbyPlayersList) {
            Optional<RugbyPlayer> existingPlayer = rugbyPlayerMapper.findPlayerById(rugbyPlayer.getId());

            if (existingPlayer.isEmpty()) {
                rugbyPlayerMapper.insertPlayerData(rugbyPlayer);
            }
        }
    }

    public List<PlayerDataResponse> findPlayersByReference(Integer height, Integer weight, String rugbyPosition) {
        List<RugbyPlayer> players = rugbyPlayerMapper.findPlayersByReference(height, weight, rugbyPosition);

        if (players.isEmpty()) {
            throw new PlayerNotFoundException("条件に該当する選手は存在しないか、条件の指定が誤っています");
        } else {
            return players.stream()
                    .map(player -> new PlayerDataResponse(player.getName(), player.getHeight(), player.getWeight(), player.getRugbyPosition()))
                    .collect(Collectors.toList());
        }
    }

    public RugbyPlayer insertRugbyPlayers(PlayerCreateForm playerCreateForm) {
        RugbyPlayer rugbyPlayer = new RugbyPlayer(playerCreateForm.getName(), playerCreateForm.getHeight(), playerCreateForm.getWeight(), playerCreateForm.getRugbyPosition());
        String shortId = UUID.randomUUID().toString().substring(0, 8);
        rugbyPlayer.setId(shortId);
        rugbyPlayerMapper.insertPlayerData(rugbyPlayer);
        return rugbyPlayer;
    }

    public void updateRugbyPlayer(String id, String name, Integer height, Integer weight, String rugbyPosition) {
        rugbyPlayerMapper.findPlayerById(id).orElseThrow(() -> new PlayerNotFoundException("当該IDを持つ選手は存在しません"));
        if (name != null && name.trim().isEmpty()) {
            throw new PlayerIllegalArgumentException("空白の入力は許可されていません");
        }

        if (rugbyPosition != null && rugbyPosition.trim().isEmpty()) {
            throw new PlayerIllegalArgumentException("空白の入力は許可されていません");
        }

        if (name == null && height == null && weight == null && rugbyPosition == null) {
            throw new PlayerIllegalArgumentException("更新するための情報が不足しています");
        }

        rugbyPlayerMapper.updateRugbyPlayer(id, name, height, weight, rugbyPosition);
    }

    public void deleteRugbyPlayer(String id) {
        rugbyPlayerMapper.findPlayerById(id).orElseThrow(() -> new PlayerNotFoundException("当該IDを持つ選手は存在しません"));
        rugbyPlayerMapper.deleteRugbyPlayer(id);
    }
}

