package com.social.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    String id;
    long userId;
    String name;
    String username;
//    String password;
//    String token;
    String country;
    String city;
    LocalDate birthday;
//    List<Long> following;
}
