package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.mapper.RugbyPlayerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RugbyPlayerInfoService {

    private RugbyPlayerMapper rugbyPlayerMapper;

    public RugbyPlayerInfoService(RugbyPlayerMapper rugbyPlayerMapper) {
        this.rugbyPlayerMapper = rugbyPlayerMapper;
    }

    public List<RugbyPlayer> findPlayersByReference(Integer height, Integer weight, String posi) {
        List<RugbyPlayer> players;

        if (height == null && weight == null && posi == null) {
            players = rugbyPlayerMapper.findAll();
        } else {
            players = rugbyPlayerMapper.findByReference(height, weight, posi);
            }

            if (players.isEmpty()) {
                throw new PlayerNotFoundException("条件に該当する選手は存在しないか、条件の指定が誤っています");
            }
            return players;
        }
    }



