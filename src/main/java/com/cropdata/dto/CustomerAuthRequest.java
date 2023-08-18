package com.cropdata.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAuthRequest {

    private String userName;
    private String password;
//    private Set<String> roles;
//    private String email;
}