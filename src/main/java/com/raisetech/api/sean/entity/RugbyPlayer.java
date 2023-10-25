package com.raisetech.api.sean.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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
