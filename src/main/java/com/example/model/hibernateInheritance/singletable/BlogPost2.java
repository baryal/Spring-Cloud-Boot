package com.example.model.hibernateInheritance.singletable;

import com.example.model.hibernateInheritance.tableperclass.Publication1;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity//(name = "BlogPost")
//@DiscriminatorValue("blog")
public class BlogPost2 extends Publication2 {

    @Column
    private String url;
}
