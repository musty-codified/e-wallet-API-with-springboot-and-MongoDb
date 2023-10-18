package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "Response data payload")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    @Schema(description = " message")
    private String message;

    @Schema(description = "")
    private boolean status;

    private T data;
}

