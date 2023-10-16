package com.raisetech.api.sean.controller;

public class PlayerDataResponse {

    private String name;

    private int height;

    private int weight;

    private String rugbyPosition;

    public PlayerDataResponse(String name, int height, int weight, String rugbyPosition) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.rugbyPosition = rugbyPosition;
    }

    public String getName() {
        return this.name;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWeight() {
        return this.weight;
    }

    public String getRugbyPosition() {
        return this.rugbyPosition;
    }
}
