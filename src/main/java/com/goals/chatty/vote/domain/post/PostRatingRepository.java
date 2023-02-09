package com.goals.chatty.vote.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostRatingRepository extends JpaRepository<PostRating, RatingIdOfPost> {

    @Modifying
    @Transactional
    @Query(value = "insert into post_rating (user_id, post_id) values(:userId, :postId)", nativeQuery = true)
    void saveRating(@Param("userId") long userId, @Param("postId") long postId) ;

    @Query(value = "select count(*) from PostRating where post_id = :postId")
    long countByPostId (@Param("postId") long postId);

    @Query(value = "select count(*) from PostRating where user_id = :userId")
    long countByCommentId (@Param("userId") long userId);

    @Modifying
    @Transactional
    @Query(value = "delete from post_rating where post_id = ?1", nativeQuery = true)
    void deleteRating(long postId);

    @Override
    @Query(value = "delete from post_rating", nativeQuery = true)
    void deleteAll();

    @Override
    List<PostRating> findAll();

}


