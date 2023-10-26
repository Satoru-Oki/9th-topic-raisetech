package com.raisetech.api.sean.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RugbyPlayer {

    private String id;

    private String name;

    private Integer height;

    private Integer weight;

    @JsonProperty("type")
    private String rugbyPosition;

    @JsonProperty("type")
    public String getRugbyPosition() {
        return rugbyPosition;
    }
}
