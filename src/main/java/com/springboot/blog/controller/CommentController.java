package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")

public class CommentController {

    private CommentService commentService;
    //create the constructor for the commentService
         public CommentController(CommentService commentService) {
            this.commentService = commentService;
        }
    @PostMapping("/posts/{postId}/comments")

    public ResponseEntity<CommentDto>createComment(@PathVariable(value = "postId") long postId,
                                                       @RequestBody CommentDto commentDto){
             return new ResponseEntity<>(commentService.createComment(postId,commentDto),HttpStatus.CREATED);

    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto>getCommentByPostId(@PathVariable(value = "postId") Long postId){
             return commentService.getCommentsByPostId(postId);
    }
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>getcommentById(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId){
             System.out.println("postId,commentId");
             CommentDto commentDto=commentService.getCommentById(postId,commentId);
             return new ResponseEntity<>(commentDto ,HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>updateComment(@PathVariable (value = "postId")Long postId,
                                                   @PathVariable(value = "id") Long commentId,
                                                   @RequestBody CommentDto commentDto){//here we bind the request so to convert the JSON to Java object ..we use @Request Body
       CommentDto updatedComment=commentService.updateComment(postId,commentId,commentDto);
       return new ResponseEntity<>(updatedComment,HttpStatus.OK);

    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value="postId") Long postId,
                                                @PathVariable (value = "id")Long commentId){
             commentService.deleteComment(postId,commentId);
             return new ResponseEntity<>("comment deleted succesfully" ,HttpStatus.OK);
    }


}
