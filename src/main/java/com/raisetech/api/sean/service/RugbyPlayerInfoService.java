package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;
import com.raisetech.api.sean.mapper.RugbyPlayerMapper;
import org.springframework.dao.DataAccessException;
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

        try {
            if (posi != null) {
                players = rugbyPlayerMapper.findByPosition(posi);
            } else if (height != null || weight != null) {
                players = rugbyPlayerMapper.findByHeightOrWeight(height, weight);
            } else {
                players = rugbyPlayerMapper.findAll();
            }

            if (players.isEmpty()) {
                throw new PlayerNotFoundException("条件に該当する選手は存在しないか、条件の指定が誤っています");
            }

            return players;
        } catch (DataAccessException e) {
            throw new RuntimeException("データベースにエラーが発生しました", e);
        }
    }
}


