package com.raisetech.api.sean.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RugbyPlayer {

    private String id;

    private String name;

    private Integer height;

    private Integer weight;

    @JsonProperty("type")
    private String rugbyPosition;


    public String getId() {
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

    public String getRugbyPosition() {
        return rugbyPosition;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setRugbyPosition(String rugbyPosition) {
        this.rugbyPosition = rugbyPosition;
    }
}
