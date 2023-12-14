package com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.impl;


import com.mustycodified.ewalletAPIwithspringbootandMongoDB.dtos.requestDtos.MailDto;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.services.EmailService;
import com.mustycodified.ewalletAPIwithspringbootandMongoDB.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final AppUtils appUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final Marker IMPORTANT = MarkerFactory.getMarker("IMPORTANT");

    @Override
    public ResponseEntity<String> sendMail(MailDto mailDto) {

        if (!appUtil.validEmail(mailDto.getTo()))
            new ResponseEntity<>("Email is not valid", HttpStatus.BAD_REQUEST);

        appUtil.isEmailDomainValid(mailDto.getTo());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ewalletappllc@gmail.com");
        message.setTo(mailDto.getTo());
        message.setSentDate(new Date());
        message.setSubject(mailDto.getSubject());
        message.setText(mailDto.getBody());

        try {

            LOGGER.info("Beginning of log *********");
            LOGGER.info(IMPORTANT, "Sending email to: " + mailDto.getTo());
            javaMailSender.send(message);
            return new ResponseEntity<>("sent", HttpStatus.OK);
        } catch (MailException exception){
            LOGGER.error(IMPORTANT, exception.getMessage());
        }
        return new ResponseEntity<>("An Error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
