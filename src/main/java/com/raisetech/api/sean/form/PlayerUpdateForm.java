package com.raisetech.api.sean.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerUpdateForm {

    private String name;

    @Min(value = 100, message = "身長は100から300の間で登録してください")
    @Max(value = 300, message = "身長は100から300の間で登録してください")
    private Integer height;

    @Min(value = 10, message = "体重は10から300の間で登録してください")
    @Max(value = 300, message = "体重は10から300の間で登録してください")
    private Integer weight;

    private String rugby_position;
}
