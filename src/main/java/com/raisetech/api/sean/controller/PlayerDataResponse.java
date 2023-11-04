package com.raisetech.api.sean.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PlayerDataResponse {

    private String name;

    private int height;

    private int weight;

    @JsonProperty("rugby_position")
    private String rugbyPosition;
}
