package com.springboot.blog.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

//@Data   //Lombok annontation that automatically generates,constructors,getters and setters,hashcode, toString in the run time
@AllArgsConstructor  //It generates all argument constructor for the class
@NoArgsConstructor//It generates no arguments for the class and hibernates internally uses proxies to create object.

@Entity
@Table(    //it is uses to map over JPA entity withMySQL database table.
        name="posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
//like the above we mention table ..If we dont mention any table..then JPA will provide the name to the table as a class name(Post)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();//Set not allowed the duplicates. // two edit

}

