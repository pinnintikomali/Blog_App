package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl  implements PostService {

    //we inject postRepository..bcz we are using the postRepository here.
    private PostRepository postRepository;

    //Inject the ModelMapper into Spring bean
    private ModelMapper mapper;
    //generate the constructor below  for postRepository
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {

        this.postRepository = postRepository;
        this.mapper=mapper;
    }
    @Override
    public PostDto createPost(PostDto postDto) { //this method returns PostDto to the Controller
          //mapToEntity
        Post post=mapToEntity(postDto);
        Post newpost=postRepository.save(post); //Save method  to return the entity

        //Convert entity to Dto
        PostDto postResponse=mapToDTO(newpost);
        return postResponse;

    }

    //Implement the methods for getAllPosts()
    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {//here we add the parameters for pagination
        //If the sort order is ascending order..the below method return ascending order..else descending order.

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        //create pageable instance
        Pageable pageable=PageRequest.of(pageNo,pageSize,sort);

        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page object
        List<Post>listOfPosts=posts.getContent();
        List<PostDto>content= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize((posts.getSize()));
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }
//PostBY Id
    @Override
    public PostDto getPostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        return mapToDTO(post);
    }
//Update method
    @Override
    public PostDto updatePost(PostDto postDto, long id) {
    //get post by id from the database
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost=postRepository.save(post);
        return mapToDTO(updatePost);
    }
//Delete postById
    @Override
    public void deletePostById(long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);

    }

    //convert the entity into DTO in the below method
    private PostDto mapToDTO(Post post){
        PostDto postDto=mapper.map(post,PostDto.class);

//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }
    //Convert DTO to entity
    private Post mapToEntity(PostDto postDto) {
        Post post=mapper.map(postDto,Post.class);

//            Post post = new Post();
//            post.setTitle(postDto.getTitle());
//            post.setDescription(postDto.getDescription());
//            post.setContent(postDto.getContent());
            return post;
        }
    }

