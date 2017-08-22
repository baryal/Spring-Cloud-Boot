package com.example.model.hibernateInheritance.mappedSuperclass;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * you can’t use the inheritance structure for polymorphic queries or to define relationships.
 * But you can, of course, query the entites as any other entity.
 * List books = em.createQuery(“SELECT b FROM Book b”, Book.class).getResultList();
 * The Book entity and all its attributes are mapped to the book table. This makes the generated query simple and efficient.
 * It just has to select all columns of the book table.
 * DEBUG [org.hibernate.SQL] – select book0_.id as id1_2_, book0_.publishingDate as publishi2_2_, book0_.title as title3_2_, book0_.version as version4_2_, book0_.pages as pages5_2_ from Book book0_
 *
 *
 * Since this strategy is used to only share Java code we should not query database for parent class(Publication) instances.
 * If we do Hibernate will execute many select statements - one for every class inheriting from Publication
 *
 *
 * NOTE: Querying for BaseEntity via JPA will throw exception with message Not an entity: class Publication.
 * We can query for Publication only via Hibernate Session object
 */
//@Entity//Should be @Entity(name="Book")
public class Book extends Publication{

    @Column
    private int pages;
}

