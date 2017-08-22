package com.example.model.hibernateInheritance.joined;

import com.example.model.hibernateInheritance.mappedSuperclass.Publication;

import javax.persistence.Column;
import javax.persistence.Entity;

//@Entity//Should be @Entity(name="BlogPost")
public class BlogPost3 extends Publication3 {

    @Column
    private String url;
}
