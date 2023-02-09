package com.goals.chatty.vote.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentRatingRepository extends JpaRepository <CommentRating, RatingIdOfComment> {
    @Modifying
    @Transactional
    @Query(value = "insert into comment_rating (user_id, comment_id) values(:userId, :commentId)", nativeQuery = true)
    void saveRating(@Param("userId") long userId, @Param("commentId") long commentId) ;

    @Override
    List<CommentRating> findAll();

    @Query(value = "delete from comment_rating where comment_id = ?1", nativeQuery = true)
    void deleteRating(long commentId);

}
