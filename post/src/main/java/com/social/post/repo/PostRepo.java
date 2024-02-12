package com.social.post.repo;

import com.social.post.model.Post;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The main interface for handling mongodb DB queries
 */
@Repository
public interface PostRepo extends MongoRepository<Post,String> {

    Window<Post> findFirst10ByUserIdInOrderByDateDesc(List<Long> userdIds, OffsetScrollPosition position);

    //("{'$or':[ {'A':10}, {'B':20} ] }")
    //@Query("{'$and':[ {'post.id':?0}, {'post.likes':{$elemMatch: { k: ?1, v: ?2 } } } ] }")
//    @Query("{'$and':[ {'post.id':?0}, {'post.likes':{$elemMatch: { k: ?1 } } } ] }")
//    Optional<Post> findByProfileLike(String postId,long userId,String username);
}
