package com.springboot.blog.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity //to make the class as a JPA Entity
@Table(name = "comments")


public class Comment {
    //create fields
    @Id // for primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // for Auto-generate
    private long id;
    private String name;
    private  String email;
    private  String body;

    @ManyToOne(fetch = FetchType.LAZY)    //For bi_directional relations between Post and Comment.
    //here Many-Comment,One -Post
    @JoinColumn(name = "post_id",nullable = false) //to specify Foreign key
    private Post post;


}
