package com.example.model.hibernateInheritance.tableperclass;

import javax.persistence.*;
import java.util.Date;

/**
 * Table per class hierarchy
 * The table per class strategy is similar to the mapped superclass strategy.
 * The main difference is that the superclass is now also an entity.
 * Each of the concrete classes gets still mapped to its own database table.
 * This mapping allows you to use polymorphic queries and to define relationships to the superclass.
 * But the table structure adds a lot of complexity to polymorphic queries, and you should, therefore, avoid them.
 *
 */

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Publication1 {

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
