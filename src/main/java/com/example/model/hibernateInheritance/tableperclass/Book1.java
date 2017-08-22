package com.example.model.hibernateInheritance.tableperclass;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity//(name = "Book")
//@DiscriminatorValue("cat")
public class Book1 extends Publication1 {

    @Column
    private int pages;
}
