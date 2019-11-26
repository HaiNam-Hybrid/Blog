package com.example.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name ="author")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Author {
    @Id
    private String id;
    private String firstName;
    private String lastName;


}
