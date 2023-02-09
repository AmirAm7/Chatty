package com.goals.chatty.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Long> {

    List<Comment> getAllByPost_Id (long post_id);
    List<Comment> getAllByUser_Id (long user_id);
    long countAllByPost_Id(long post_id);
    void deleteAllByPost_Id(long post_id);
}


// @Query(value = "delete from Comment c where c.post.postId = ?1")