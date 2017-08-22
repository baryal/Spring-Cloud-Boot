package com.example.model.hibernateInheritance.singletable;

import com.example.model.hibernateInheritance.tableperclass.Publication1;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity//(name = "Book")
//@DiscriminatorValue("book")
public class Book2 extends Publication2 {

    @Column
    private int pages;
}
