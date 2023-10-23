package com.raisetech.api.sean.service;

import com.raisetech.api.sean.entity.RugbyPlayer;

import java.util.List;

class TeamInfo {
    private List<RugbyPlayer> players;

    public List<RugbyPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<RugbyPlayer> players) {
        this.players = players;
    }
}
