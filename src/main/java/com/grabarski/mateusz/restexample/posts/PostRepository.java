package com.grabarski.mateusz.restexample.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Mateusz Grabarski on 24.07.2018.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

}