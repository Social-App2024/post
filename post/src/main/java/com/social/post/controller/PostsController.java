package com.social.post.controller;

import com.social.post.model.Post;
import com.social.post.service.PostsService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.apache.tomcat.util.http.fileupload.FileItemIterator;
//import org.apache.tomcat.util.http.fileupload.FileItemStream;
//import org.apache.tomcat.util.http.fileupload.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
//import org.apache.commons.fileupload.FileItemIterator;
//import org.apache.commons.fileupload.FileItemStream;
//import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.fileupload2.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import reactor.core.publisher.Mono;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The main class for receiving Rest API calls
 */
@RestController
@CrossOrigin
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    PostsService service;

    /**
     * Create new text post
     */
    @PostMapping("/post")
    public Post savePost(@RequestBody Post post)
    {
        return service.savePost(post);
    }

//    @PostMapping("/upload")
//    public Post savePost(HttpServletRequest request) throws Exception {
//        ServletFileUpload upload = new ServletFileUpload();
//        FileItemIterator iterStream = upload.getItemIterator(request);
//        Post post=null;
//        while (iterStream.hasNext()) {
//
//            FileItemStream item = iterStream.next();
//            String name = item.getFieldName();
//            InputStream stream = item.openStream();
//            if (!item.isFormField()) {
//                String filename=request.getHeader("filename");
//                post=new Post();
//                post.setCategory(request.getHeader("category"));
//                post.setUserId(Long.parseLong(request.getHeader("userid")));
//                post.setDate(LocalDate.now());
//
//                // Process the InputStream
//                OutputStream out = new FileOutputStream("D:\\mine\\Projects\\Social App\\downloads\\"+filename);
//                IOUtils.copy(stream, out);
//                out.close();
//                post.setFile("D:\\mine\\Projects\\Social App\\downloads\\"+filename);
//            } /*else {
//                String formFieldValue = Streams.asString(stream);
//            }*/
//
//
//        }
//        if(post!=null)
//            post=service.savePost(post);
//        return post;
//    }

    /**
     * Save post with media content, currently saved in local file system
     * please update the upload path below in setFile method,
     * front end sample in resources/static/save_media_post.html
     * @param request
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
//    public Post savePost(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
    public Post savePost(MultipartHttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        String filename= file.getOriginalFilename();//request.getHeader("filename");
        Post post=new Post();
        post.setCategory(request.getParameter("category"));
        post.setUserId(Long.parseLong(request.getParameter("userid")));
        post.setDate(LocalDate.now());

        // Process the InputStream
        OutputStream out = new FileOutputStream("D:\\mine\\Projects\\Social App\\downloads\\"+filename);
        IOUtils.copy(file.getInputStream(), out);
        out.close();
        post.setFile("D:\\mine\\Projects\\Social App\\downloads\\"+filename);

        post=service.savePost(post);
        return post;
    }

    /**
     * Used by HTML <video> tag to display video content of a post
     * @param postId
     * @param range used internally
     * @return
     */
    @GetMapping(value = "/video/{postId}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String postId, @RequestHeader("Range") String range)
    {
        return service.getVideo(postId);
    }

    @GetMapping("/like/{postId}")
    public void likePost(@PathVariable String postId,@RequestParam long userId)
    {
        service.likePost(postId,userId);
    }

    @GetMapping("/unlike/{postId}")
    public void unlikePost(@PathVariable String postId,@RequestParam long userId)
    {
        service.unlikePost(postId,userId);
    }

//    @GetMapping("/likes/{postId}")

}
