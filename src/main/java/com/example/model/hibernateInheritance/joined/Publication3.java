package com.example.model.hibernateInheritance.joined;

import javax.persistence.*;
import java.util.Date;

/**
 * Joined
 *
 * The joined table approach maps each class of the inheritance hierarchy to its own database table.
 * This sounds similar to the table per class strategy. But this time,
 * also the abstract superclass Publication gets mapped to a database table.
 * This table contains columns for all shared entity attributes. The tables of the subclasses are much smaller
 * than in the table per class strategy. They hold only the columns specific for the mapped entity class and a
 * primary key with the same value as the record in the table of the superclass.
 *
 * Each query of a subclass requires a join of the 2 tables to select the columns of all entity attributes.
 * That increases the complexity of each query, but it also allows you to use not null constraints on subclass
 * attributes and to ensure data integrity. The definition of the superclass Publication is similar to the
 * previous examples. The only difference is the value of the inheritance strategy which is InheritanceType.JOINED.
 *
 * The definition of the subclasses doesn’t require any additional annotations.
 * They just extend the superclass, provide an @Entity annotation and define the mapping of their specific attributes.
 *
 *
 * the columns mapped by each subclass are stored in 2 different database tables.
 * The publication table contains all columns mapped by the superclass Publication and the book table all columns mapped by the Book entity.
 * Hibernate needs to join these 2 tables by their primary keys to select all attributes of the Book entity.
 * This is an overhead that makes these queries slightly slower than the simpler queries generated for the single table strategy.
 *
 */

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Publication3 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @Column
    protected String title;

    @Version
    @Column(name = "version")
    private int version;

    @Column
    @Temporal(TemporalType.DATE)
    private Date publishingDate;
}
