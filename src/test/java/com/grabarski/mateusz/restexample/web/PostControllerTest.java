package com.grabarski.mateusz.restexample.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grabarski.mateusz.restexample.posts.Post;
import com.grabarski.mateusz.restexample.posts.PostController;
import com.grabarski.mateusz.restexample.posts.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Mateusz Grabarski on 24.07.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostRepository postRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnCreatedPost() throws Exception {
        // given
        Post postToAdd = new Post("title", "message");
        Post addedPost = new Post(1, "title", "message");

        when(postRepository.save(postToAdd)).thenReturn(addedPost);

        // when
        ResultActions result = mockMvc
                .perform(post("/posts")
                        .content(objectMapper.writeValueAsString(postToAdd))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content()
                        .string(objectMapper.writeValueAsString(addedPost)));
    }

    @Test
    public void shouldReturnAllPosts() throws Exception {
        // given
        Post post1 = new Post(1, "title1", "message1");
        Post post2 = new Post(2, "title2", "message2");

        List<Post> postsToReturn = Arrays.asList(post1, post2);

        when(postRepository.findAll()).thenReturn(postsToReturn);

        // when
        ResultActions result = mockMvc
                .perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(postsToReturn)));
    }

    @Test
    public void shouldCreateNewPostWhenInDatabaseAreNotPostWithTypedId() throws Exception {
        // given
        Long id = 1L;
        Post postToSave = new Post(id, "title", "message");

        when(postRepository.findById(id)).thenReturn(Optional.empty());
        when(postRepository.save(postToSave)).thenReturn(postToSave);

        // when
        ResultActions result = mockMvc
                .perform(put("/posts/" + id)
                        .content(objectMapper.writeValueAsString(postToSave))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(postToSave)));
    }

    @Test
    public void shouldUpdatePostInPUTEndpoint() throws Exception {
        // given
        Long id = 1L;
        Post postToUpdate = new Post(id, "title", "message");
        Post updatedPost = new Post(id, "title", "updated message");

        when(postRepository.findById(id)).thenReturn(Optional.of(postToUpdate));
        when(postRepository.save(postToUpdate)).thenReturn(updatedPost);

        // when
        ResultActions result = mockMvc
                .perform(put("/posts/" + id)
                        .content(objectMapper.writeValueAsString(postToUpdate))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedPost)));
    }
}