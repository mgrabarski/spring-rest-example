package com.grabarski.mateusz.restexample.posts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Mateusz Grabarski on 24.07.2018.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue
    private long id;

    private String title;
    private String message;

    public Post(String title, String message) {
        this.title = title;
        this.message = message;
    }
}