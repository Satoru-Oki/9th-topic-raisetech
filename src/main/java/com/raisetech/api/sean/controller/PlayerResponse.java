package com.raisetech.api.sean.controller;

import com.raisetech.api.sean.entity.RugbyPlayer;

public class PlayerResponse {

    private String name;
    private String position;

    public PlayerResponse(RugbyPlayer name) {
        this.name = name.getName();
        this.position = name.getPosition();
    }


    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }
}
