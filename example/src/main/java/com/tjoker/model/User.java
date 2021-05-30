package com.tjoker.model;


import lombok.Data;

@Data
public class User {
    private String id;

    private String name;

    private String password;

    private String email;

    private Integer count;


}