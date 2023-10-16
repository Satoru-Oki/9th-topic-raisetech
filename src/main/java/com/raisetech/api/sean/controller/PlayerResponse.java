package com.raisetech.api.sean.controller;

import com.raisetech.api.sean.entity.RugbyPlayer;

public class PlayerResponse {

    private String name;
    private String rugbyPosition;

    public PlayerResponse(RugbyPlayer name) {
        this.name = name.getName();
        this.rugbyPosition = name.getRugbyPosition();
    }

    public String getName() {
        return this.name;
    }

    public String getRugbyPosition() {
        return this.rugbyPosition;
    }
}
