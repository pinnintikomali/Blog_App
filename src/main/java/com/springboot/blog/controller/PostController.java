package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController { //Here PostController as a spring bean.so we need to create only one constructor and omit the @autowired.
    //now we inject post service class in this controller
    private PostService postService;    /*Post service is an interface.
    so we are using loosely coupling,not tightly coupled with this classes*/

    //create the constructor for PostService
    public PostController(PostService postService) {

        this.postService = postService;
    }

//create the Rest API--create blog post
@PostMapping
public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
}    /*created the method createPost and Providing the PostDto
     as a parameter and returning the ResponseEntity object with response
      of createPost method and Http status*/

    //get all posts rest api
    @GetMapping
    public PostResponse getAllPosts(
        //here write for pagination..by proving 2 parameters -->page num,page size.
        @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
        @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
        @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_ID,required = false)String sortBy,
        @RequestParam(value = "sortDir",defaultValue =AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortDir
        ){

        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);

    }//get post by id
    @GetMapping("/{id}")
    public  ResponseEntity<PostDto>getPostById(@PathVariable(name = "id")long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //update post by id rest api
    @PutMapping("/{id}")
    public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto,@PathVariable(name="id")long id){
         PostDto postResponse= postService.updatePost(postDto,id);
         return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    // Delete Post rest Api
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deletePost(@PathVariable(name="id")long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);

    }

}
