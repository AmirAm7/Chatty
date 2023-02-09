package com.goals.chatty.vote.service;

public interface IVoteService {
    Long userVotePost (long post_id);
    Long userVoteComment (long comment_id);
    long getCountOfVotesByPostId(long post_id);
    long getCountOfVotesByCommentId(long comment_id);
    void removeVoteOfPost(long comment_id);
    void removeVoteOfComment(long comment_id);
}
