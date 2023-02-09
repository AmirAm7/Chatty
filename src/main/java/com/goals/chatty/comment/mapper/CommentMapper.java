package com.goals.chatty.comment.mapper;

import com.goals.chatty.comment.domain.Comment;
import com.goals.chatty.comment.rest.response.CommentDto;
import com.goals.chatty.user.service.UserServiceImpl;
import com.goals.chatty.userManagement.config.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final UserServiceImpl userService;
    @Autowired
    public CommentMapper(ModelMapper modelMapper, CurrentUser currentUser, UserServiceImpl userService) {
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.userService = userService;
    }

    public CommentDto convertToCommentDTO (Comment comment){
        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);
        commentDto.setPostId(comment.getId());
        commentDto.setAuthor(comment.getAuthor());
        commentDto.setTextOfComment(comment.getText());
        commentDto.setCreationTime(LocalDateTime.now());
        return commentDto;
    }

    public Comment convertToComment (CommentDto commentDTO){
        Comment newComment = modelMapper.map(commentDTO, Comment.class);
        newComment.setCreationTime(LocalDateTime.now());
        newComment.setAuthor(currentUser.getUsername());
        newComment.setUser(userService.findByUsername(currentUser.getUsername()));
        return newComment;
    }

    public List<CommentDto> convertToListOfCommentDto (List <Comment> comments){

        List<CommentDto> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto newCommentDto = convertToCommentDTO(comment);
            commentDTOs.add(newCommentDto);
        }
        return commentDTOs;
    }

    public List<Comment> convertToListOfComment (List <CommentDto> commentDtos){

        List<Comment> comments = new ArrayList<>();

        for (CommentDto commentDto: commentDtos
             ) {
            Comment comment = convertToComment(commentDto);
            comments.add(comment);
        }
        return comments;
    }

}




