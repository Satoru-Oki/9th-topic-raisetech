package com.raisetech.api.sean.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerDataResponse {

    private String name;

    private int height;

    private int weight;

    @JsonProperty("type")
    private String rugbyPosition;
}
