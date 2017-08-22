package com.example.model.hibernateInheritance.joined;

import com.example.model.hibernateInheritance.mappedSuperclass.Publication;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * List books = em.createQuery(“SELECT b FROM Book b”, Book.class).getResultList();
 * DEBUG [org.hibernate.SQL] – select book0_.id as id1_3_, book0_.publishingDate as publishi2_3_, book0_.title as title3_3_, book0_.version as version4_3_, book0_.pages as pages1_2_ from Book book0_
 *
 */
//@Entity//Should be @Entity(name="Book")
public class Book3 extends Publication3 {

    @Column
    private int pages;
}

