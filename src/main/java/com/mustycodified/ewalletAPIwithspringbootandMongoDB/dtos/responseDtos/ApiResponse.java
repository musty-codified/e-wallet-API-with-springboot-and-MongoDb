package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.responseDtos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "Api Response containing metadata sent back to the user")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    @Schema(description = " message metadata")
    private String message;

    @Schema(description = "status metadata - true or false")
    private boolean status;
    private T data;
}

