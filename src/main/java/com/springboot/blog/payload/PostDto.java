package com.springboot.blog.payload;

import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    //clients wants the fields want we mention the POST resource.
    private long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentDto> comments;

}
