package com.example.model.hibernateInheritance.mappedSuperclass;

import javax.persistence.Column;
import javax.persistence.Entity;

//@Entity//Should be @Entity(name="BlogPost")
public class BlogPost extends Publication{

    @Column
    private String url;
}
