package com.raisetech.api.sean.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.raisetech.api.sean.entity.RugbyPlayer;

public class PlayerResponse {

    private String name;

    @JsonProperty("type")
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

    public void setRugbyPosition(String rugbyPosition) {
        this.rugbyPosition = rugbyPosition;
    }

}
