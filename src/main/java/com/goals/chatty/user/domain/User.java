package com.goals.chatty.user.domain;

import com.goals.chatty.comment.domain.Comment;
import com.goals.chatty.userManagement.security.valitation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;
    @Column(name = "last_name",nullable = false, length = 30)
    private String lastName;
    @Column(name = "email", nullable = false)
    @ValidEmail
    private String email;
    @Column(name = "username",nullable = false, length = 20)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @Getter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "followingTable")
    List<User> followeds = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "followerTable")
    List<User> followers = new ArrayList<>();

    @OneToMany //baut a third table
    @JoinTable(name = "user_comments") // With this you can change the name
    List<Comment> listOfComments = new ArrayList<>();

    @OneToMany
    @JoinTable(name = "user_posts")
    List<Comment> listOfPosts = new ArrayList<>();

}






