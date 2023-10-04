package com.raisetech.api.sean.entity;

public class RugbyPlayer {

    private int id;
    private String name;
    private int height;
    private int weight;
    private String position;

    public RugbyPlayer(int id, String name, int height, int weight, String position) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.position = position;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getPosition() {
        return position;
    }
}
