package com.themarket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BaseEnum {
    Success("S", "success"),
    Fail("F", "fail");

    private String code;
    private String description;

}
