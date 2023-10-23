package com.raisetech.api.sean.service;

import com.raisetech.api.sean.controller.PlayerDataResponse;
import com.raisetech.api.sean.controller.PlayerResponse;
import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.mapper.RugbyPlayerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RugbyPlayerInfoService {

    private RugbyPlayerMapper rugbyPlayerMapper;

    public RugbyPlayerInfoService(RugbyPlayerMapper rugbyPlayerMapper) {
        this.rugbyPlayerMapper = rugbyPlayerMapper;
    }

    public void insertRugbyPlayers(List<RugbyPlayer> rugbyPlayersList) {
        for (RugbyPlayer rugbyPlayer : rugbyPlayersList) {
            Optional<RugbyPlayer> existingPlayer = rugbyPlayerMapper.findPlayerById(rugbyPlayer.getId());

            if (!existingPlayer.isPresent()) { // existingPlayerがnullの場合のみデータベースへ登録（重複登録回避）
                if (Objects.nonNull(rugbyPlayer.getName()) &&
                        Objects.nonNull(rugbyPlayer.getHeight()) &&
                        Objects.nonNull(rugbyPlayer.getWeight()) &&
                        Objects.nonNull(rugbyPlayer.getRugbyPosition())) {
                    rugbyPlayerMapper.insertPlayerData(rugbyPlayer);
                } else {
                    throw new IncompletePlayerInfoException("IDに紐づくデータが登録されていません");
                }
            }
        }
    }

    public ResponseEntity<?> findPlayersByReference(Integer height, Integer weight, String rugbyPosition) {
        List<RugbyPlayer> players = rugbyPlayerMapper.findPlayersByReference(height, weight, rugbyPosition);

        if (players.isEmpty()) {
            throw new PlayerNotFoundException("条件に該当する選手は存在しないか、条件の指定が誤っています");
        }

        if (Objects.nonNull(height) || Objects.nonNull(weight)) {
            List<PlayerResponse> playerResponses = players.stream()
                    .map(PlayerResponse::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(playerResponses, HttpStatus.OK);
        } else {
            List<PlayerDataResponse> playerDataResponses = players.stream()
                    .map(player -> new PlayerDataResponse(player.getName(), player.getHeight(), player.getWeight(), player.getRugbyPosition()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(playerDataResponses, HttpStatus.OK);
        }
    }
}
