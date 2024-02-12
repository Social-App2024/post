package com.social.post.controller;

import com.social.post.model.Post;
import com.social.post.service.PostsService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The main class for receiving GraphQL API calls
 */
@RestController
@CrossOrigin
public class PostGraphQLController {
    @Autowired
    PostsService service;

    /**
     * Show first 10 posts of the list of following userids
     * @param userids following list
     * @param userId opening profile userid
     * @param offset used internally for scrolling, start at -1
     * @return
     */
    @QueryMapping
    public List<Post> showAllPosts(@Argument List<Long> userids, @Argument long userId, @Argument long offset)
    {
        return service.showAllPosts(userids,offset);
    }

    /**
     * Boolean flag to check if a post is liked by the opening profile
     * @param environment
     * @param post
     * @return
     */
    @SchemaMapping
    public boolean isLiked(DataFetchingEnvironment environment, Post post)
    {
        //long userId=(Long)environment.getVariables().get("userId");
        long userId=environment.getExecutionStepInfo().getParent().getArgument("userId");
        return service.doesUserLikePost(post.getId(), userId);
    }

    /**
     * Number of likes for a specific post
     * @param post
     * @return
     */
    @SchemaMapping int nlikes(Post post)
    {
        List<Long> likes=post.getLikes();
        if(likes==null)
            return 0;
        return likes.size();
    }
}
