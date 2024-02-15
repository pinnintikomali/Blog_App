package com.springboot.blog.payload;

import lombok.Data;

@Data
public class CommentDto {
    //write the filed that what  I want to show the response of API

    private long id;
    private String name;
    private String email;
    private String body;
}


