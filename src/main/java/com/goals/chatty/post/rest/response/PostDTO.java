package com.goals.chatty.post.rest.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goals.chatty.post.domain.Post;
import com.goals.chatty.user.domain.User;
import com.goals.chatty.user.mapper.Mapper;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostDTO {

    @NotNull
    @Size(min = 3, max = 30, message = "title must be between 3 and 30")
    private String title;
    @NotNull
    @Size(min = 5, max = 300, message = "title must be between 5 and 300")
    private String text;
    private String author;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime creationTime;
}
