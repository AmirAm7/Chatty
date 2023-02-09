package com.goals.chatty.comment.domain;

import com.goals.chatty.post.domain.Post;
import com.goals.chatty.user.domain.User;
import com.goals.chatty.vote.domain.post.PostRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", nullable = false)
    private Long id;
    @Column (name = "text")
    private String text;
    @Column (name = "author")
    private String author;
    @Column(name="creation_time", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime creationTime;

    @ManyToOne(targetEntity = Post.class)
    @JoinColumn(name = "post_id",  nullable=false)
    private Post post;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    List<PostRating> ratings = new ArrayList<>();
}
