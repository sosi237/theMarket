package com.themarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Schema(title = "공통 결과 DTO")
public class ResponseDTO {

    @Schema(description = "성공여부 코드", example = "S")
    private String code;

    @Schema(description = "메세지", example = "success")
    private String message;

}
