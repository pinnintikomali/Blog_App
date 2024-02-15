package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {
    //here PostDto is the return type
    //and also create the class that implements this particular interface
    PostDto createPost(PostDto postDto);//It can be implemented in the PostServiceImpl class.

    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto getPostById(long Id);

    PostDto updatePost(PostDto postDto,long id);

    void deletePostById(long id);


}