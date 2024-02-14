package com.social.post.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("post")
public class Post {
    @Id
    String id;
    long userId;
    String content;
    String text;
    String contentType;
    String category;
    String file;
    List<Long> likes;
    LocalDate date;
}
