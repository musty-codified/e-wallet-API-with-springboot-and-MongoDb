package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services;

import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.MailDto;
import org.springframework.http.ResponseEntity;

public interface EmailService {

    ResponseEntity<String> sendMail(MailDto mailDto);

}
