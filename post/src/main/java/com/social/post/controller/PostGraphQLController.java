package com.social.post.controller;

import com.social.post.model.Post;
import com.social.post.service.PostsService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostGraphQLController {
    @Autowired
    PostsService service;

    @QueryMapping
    public List<Post> showAllPosts(@Argument List<Long> userids, @Argument long userId, @Argument long offset)
    {
        return service.showAllPosts(userids,offset);
    }

    @SchemaMapping
    public boolean isLiked(DataFetchingEnvironment environment, Post post)
    {
        //long userId=(Long)environment.getVariables().get("userId");
        long userId=environment.getExecutionStepInfo().getParent().getArgument("userId");
        return service.doesUserLikePost(post.getId(), userId);
    }

    @SchemaMapping int nlikes(Post post)
    {
        List<Long> likes=post.getLikes();
        if(likes==null)
            return 0;
        return likes.size();
    }
}
