package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
// CommentServiceImpl act as a Spring bean.so we can inject it in other classes.
public class CommentServiceImpl implements CommentService {//after implementation we need to implemented the methods


    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;
    //constructor can be created for CommentRepository
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper=mapper;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        //need to convert commentDto to comment

        Comment comment=mapToEntity(commentDto);
        //retrive post entity by id then we need  to inject PostRepository in this class
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId));
        //set post to comment entity
        comment.setPost(post);
        //save comment entity to DB
        Comment newComment= commentRepository.save(comment);


        return  mapToDTO(newComment);

    }
    //to implements the methods to get all the posts
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
//Retrive comments by postId
        List<Comment>comments=commentRepository.findByPostId(postId);
        //convert list of comment entity to list of comment dto's
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }
    //to implement for postId and commentId dynamically
    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        //retrive post entity by Id
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));


        //retrive comment entity by Id
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));

        // Check if the comment belongs to the provided post
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
        //retrive the post entity by Id
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId));
        //retrive the comment entity by Id
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","id",commentId));
        // Check if the comment belongs to the provided post or not
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belogs to post");
        }
        //for update the comments body to set from comment request
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment= commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        //retrive the post entity by Id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        //retrive the comment entity by Id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check if the comment belongs to the provided post or not
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belogs to post");
        }

        commentRepository.delete(comment);
    }


    //DTO to Entity
    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto=mapper.map(comment,CommentDto.class);//object source ->comment,Destination-->CommentDto
//        CommentDto commentDto=new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
    //Entity to DTO
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=mapper.map(commentDto,Comment.class);
//        Comment comment=new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }


}
