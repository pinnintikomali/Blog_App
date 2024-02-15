package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

  //we need not to annotation this interface as @Repository bcz its already implements internally
public interface PostRepository  extends JpaRepository <Post,Long>{//post is the entity and Long is the primary key type
    /*  here no need to write any code .bcz spring data JPa has Jpa repository interface,Its provides CRUD operations and implementation   */

}
