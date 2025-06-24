package com.isaacbcdev.spring.boot.datajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonDTO {

    private String name;
    private String lastname;
}