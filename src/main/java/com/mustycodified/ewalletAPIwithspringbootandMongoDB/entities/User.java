package com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("users")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class User extends BaseEntity{

    private static final long serialVersionUID = 2L;
    @Indexed(unique = true)
    private String uuid;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    private String roles;

    @Column(nullable = false)
    private String password;

    private Date lastLoginDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "home_address")
    private String homeAddress;

    @Column(length = 15)
    private String mobileNumber;

    public User(){
        super();
    }

}
