package com.knf.dev.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "firstname")
    private String firstName;

    private String email;

    @Column(name = "lastname")
    private String lastName;

    private  String gender;
}