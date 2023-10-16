package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.mapper.RugbyPlayerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RugbyPlayerInfoService {

    private RugbyPlayerMapper rugbyPlayerMapper;

    public RugbyPlayerInfoService(RugbyPlayerMapper rugbyPlayerMapper) {
        this.rugbyPlayerMapper = rugbyPlayerMapper;
    }

    public List<RugbyPlayer> findPlayersByReference(Integer height, Integer weight, String rugbyPosition) {
        List<RugbyPlayer> players;

        if (Objects.isNull(height) && Objects.isNull(weight) && Objects.isNull(rugbyPosition)) {
            players = rugbyPlayerMapper.findAll();
        } else {
            players = rugbyPlayerMapper.findByReference(height, weight, rugbyPosition);
            }

            if (players.isEmpty()) {
                throw new PlayerNotFoundException("条件に該当する選手は存在しないか、条件の指定が誤っています");
            }
            return players;
        }
    }
