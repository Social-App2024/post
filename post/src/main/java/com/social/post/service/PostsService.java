package com.social.post.service;

import com.social.post.model.Post;
import com.social.post.repo.PostRepo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for handling API logic
 */
@Service
public class PostsService {
    private static final String FILEPATH="D:\\mine\\Projects\\Social App\\downloads\\%s";
    @Autowired
    PostRepo repo;
    @Autowired
    ResourceLoader resourceLoader;
    public Post savePost(Post post) {
        post.setDate(LocalDate.now());
        post.getUserId();
        post.getContent();
        return repo.save(post);
    }

    public Mono<Resource> getVideo(String postId)
    {
        Optional<Post> postOp=repo.findById(postId);
        if(postOp.isPresent())
        {
            Post post=postOp.get();
            return Mono.fromSupplier(()->resourceLoader.getResource(String.format(FILEPATH,post.getFile())));
        }
        return null;
    }

    public void likePost(String postId, long userId) {
        Optional<Post> postOp=repo.findById(postId);
        if(postOp.isPresent()) {
            Post post = postOp.get();
            List<Long> likes=post.getLikes();
            if(likes==null) {
                likes=new ArrayList<>();
            }
            likes.add(userId);
            post.setLikes(likes);
            repo.save(post);
        }
    }

    public void unlikePost(String postId, long userId) {
        Optional<Post> postOp=repo.findById(postId);
        if(postOp.isPresent()) {
            Post post = postOp.get();
            List<Long> likes=post.getLikes();
            if(likes!=null) {
                likes.remove(userId);
                post.setLikes(likes);
                repo.save(post);
            }
        }
    }

    /**
     * Checks if the post is liked by the opeining profile
     * @param postId
     * @param userId profile id
     * @return
     */
    public boolean doesUserLikePost(String postId, long userId)
    {
        boolean liked=false;
        Optional<Post> postOp=repo.findById(postId);
        if(postOp.isPresent())
        {
            Post post=postOp.get();
            liked = post.getLikes()==null ? false: post.getLikes().contains(userId);
        }
        return liked;
    }

    /**
     * Show first 10 posts of the list of following userids
     * @param userids
     * @param offset used internally for scrolling, start at -1
     * @return
     */
    public List<Post> showAllPosts(List<Long> userids, long offset) {
        Window<Post> posts=null;
        List<Post> postsView=new ArrayList<>();
        if(offset==-1) {
            posts = repo.findFirst10ByUserIdInOrderByDateDesc(userids, ScrollPosition.offset());
        }
        else{
//            users = repo.findFirst1ByUserIdIn(following, (OffsetScrollPosition) users.positionAt(offset));
            posts = repo.findFirst10ByUserIdInOrderByDateDesc(userids, ScrollPosition.offset(offset));
        }
//        WindowIterator<Profile> usersIt = WindowIterator.of(position -> repo.findFirst50ByUserIdIn(following, position))
//                .startingAt(OffsetScrollPosition.initial());

        //users.toList().stream().map(profile -> followingList.add(new Follower(profile.getUserId(),profile.getName())));
        postsView=posts.get().map(post -> new Post(post.getId(),post.getUserId(),post.getContent(),
                                                post.getCategory(),post.getFile(),post.getLikes(),
                                                post.getDate())).collect(Collectors.toList());
        // display images in Base64 format, for efficient transmission
        postsView.stream().filter(post -> post.getCategory().equals("photo")).
                forEach(post -> {
                    try {
                        post.setContent(getImageStream(post));
                    } catch (Exception e) {
                        //throw new RuntimeException(e);
                        e.printStackTrace();
                    }
                });

        return postsView;
    }

    /**
     * Helper method to display a post file in Base64
     * @param post
     * @return
     * @throws Exception
     */
    private String getImageStream(Post post) throws Exception {
        InputStream sourceStream  = new FileInputStream(post.getFile());
        byte[] sourceBytes = IOUtils.toByteArray(sourceStream);

        String encodedString = Base64.getEncoder().encodeToString(sourceBytes);
        return encodedString;
    }
}
