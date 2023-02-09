package com.goals.chatty.post.mapper;

import com.goals.chatty.post.domain.Post;
import com.goals.chatty.post.rest.response.PostDTO;
import com.goals.chatty.user.domain.User;
import com.goals.chatty.user.service.UserServiceImpl;
import com.goals.chatty.userManagement.config.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper {

    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final UserServiceImpl userService;
    @Autowired
    public PostMapper(ModelMapper modelMapper, CurrentUser currentUser, UserServiceImpl userService) {
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.userService = userService;
    }

    public Post convertToPost (PostDTO postDTO){
        Post newPost = modelMapper.map(postDTO, Post.class);
        User user =  userService.findByUsername(currentUser.getUsername());
        newPost.setCreationTime(LocalDateTime.now());
        newPost.setAuthor(user);
        return newPost;
    }

    public PostDTO convertToPostDTO (Post post){
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        postDTO.setAuthor(post.getAuthor().getUsername());
        postDTO.setCreationTime(post.getCreationTime());
        return postDTO;
    }

    public List<PostDTO> convertToListOfPostDto(List<Post> listOfPosts){
        PostDTO postDto = new PostDTO();
        List<PostDTO> listOfPostDto = new ArrayList<>();
        for (Post post: listOfPosts
             ) {
            postDto.setAuthor(post.getAuthor().getUsername());
            postDto.setTitle(post.getTitle());
            postDto.setText(post.getText());
            postDto.setCreationTime(post.getCreationTime());
            listOfPostDto.add(postDto);
        }
        return listOfPostDto;
    }
}

