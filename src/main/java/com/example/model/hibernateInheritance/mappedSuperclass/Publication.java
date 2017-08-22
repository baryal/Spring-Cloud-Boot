package com.example.model.hibernateInheritance.mappedSuperclass;

import javax.persistence.*;
import java.util.Date;

/**
 * https://marcin-chwedczuk.github.io/mapping-inheritance-in-hibernate
 * No inheritance: This strategy is used if we want to share Java code between entity classes.
 */
//@MappedSuperclass
public abstract class Publication {

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
