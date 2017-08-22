package com.example.model.hibernateInheritance.singletable;

import javax.persistence.*;
import java.util.Date;

/**
 *Single Table
 *The single table strategy maps all entities of the inheritance structure to the same database table.
 * This approach makes polymorphic queries very efficient and provides the best performance.
 *
 * A special column called discriminator is added to that table to help Hibernate know which subclass is stored in given row.
 *
 *To enable table per hierarchy strategy, superclass must be marked as @Entity and must have @Inheritance(strategy = InheritanceType.SINGLE_TABLE) annotation.
 *  We can choose discriminator column name and type using  @DiscriminatorColumn annotation.
 *  Subclasses must be marked as @Entity and can provide values for discriminator column
 *  via @DiscriminatorValue annotation (discriminator value defaults to class name).
 *
 *
 *  But it also has some drawbacks. The attributes of all entities are mapped to the same database table.
 *  Each record uses only a subset of the available columns and sets the rest of them to null.
 *  You can, therefore, not use not null constraints on any column that isn’t mapped to all entities.
 *  That can create data integrity issues, and your database administrator might not be too happy about it.
 *
 *
 *  The definition of the subclasses is again similar to the previous examples.
 *  But this time, you should also provide a @DiscriminatorValue annotation.
 *  It specifies the discriminator value for this specific entity class so
 *  that your persistence provider can map each database record to a concrete entity class.
 *  The @DiscriminatorValue annotation is optional if you use Hibernate. If you don’t provide a discriminator value,
 *  Hibernate will use the simple entity name by default. But this default handling isn’t defined by the JPA specification,
 *  and you shouldn’t rely on it.
 */

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "publication_type")
public abstract class Publication2 {

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
