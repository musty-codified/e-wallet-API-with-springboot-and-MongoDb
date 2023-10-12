package com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailDto {
    private String to;
    private String subject;
    private String body;

}
