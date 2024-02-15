package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto>getCommentsByPostId(long PostId);

    //for getting postId and commentId Dynamically
    CommentDto getCommentById(Long postId,Long commentId);
     //update the Comment
    CommentDto updateComment(Long postId,long commentId,CommentDto commentRequest);
    //Delete by Id
    void deleteComment(Long postId,Long commentId);




}
