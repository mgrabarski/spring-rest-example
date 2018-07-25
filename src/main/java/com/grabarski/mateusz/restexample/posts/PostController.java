package com.grabarski.mateusz.restexample.posts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mateusz Grabarski on 24.07.2018.
 */
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody Post postToAdd) {
        return postRepository.save(postToAdd);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id, @RequestBody Post postToAdd) {
        Optional<Post> postAsOptional = postRepository.findById(id);
        if (!postAsOptional.isPresent()) {
            return new ResponseEntity<>(postRepository.save(postToAdd), HttpStatus.OK);
        }
        Post post = postAsOptional.get();
        post.setTitle(postToAdd.getTitle());
        post.setMessage(postToAdd.getMessage());
        return new ResponseEntity<>(postRepository.save(post), HttpStatus.OK);
    }
}