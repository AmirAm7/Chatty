package com.goals.chatty.comment.rest.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long postId;
    @NotNull
    private String textOfComment;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime creationTime;
    @NotNull
    private String author;

}
