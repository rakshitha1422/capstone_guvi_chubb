package com.ouction.ouction_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    @Column(name="user_name", length = 255)
    private String username;

    @Column(name="user_password", length = 255)
    private String userpassword;

    @Column(name="user_email", length = 30)
    private String useremail;

    @Column(name="user_role", length = 30)
    private String userrole;

    @Column(name="Isverified", length = 10)
    private boolean userverify;

    @Column(name="user_date_joined")
    private LocalDate userdatejoined;



}
