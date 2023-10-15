package com.raisetech.api.sean.controller;

import com.raisetech.api.sean.entity.RugbyPlayer;

public class PlayerResponse {

    private String name;
    private String posi;

    public PlayerResponse(RugbyPlayer name) {
        this.name = name.getName();
        this.posi = name.getPosi();
    }


    public String getName() {
        return name;
    }

    public String getPosi() {
        return posi;
    }
}
