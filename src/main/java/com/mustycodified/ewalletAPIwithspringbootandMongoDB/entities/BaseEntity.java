package com.mustycodified.ewalletAPIwithspringbootandMongoDB.entities;

import javax.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    private String id;
    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;

    BaseEntity(){
        this.createdDate= new Date();
        this.updatedDate = new Date();
    }

    @PrePersist
    public void setCreatedAt() {
        createdDate = new Date();
    }
    @PreUpdate
    public void setUpdatedAt() {
        updatedDate = new Date();
    }
}
