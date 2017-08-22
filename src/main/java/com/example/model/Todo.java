package com.example.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NamedNativeQuery(name = "Todo.findBySearchTermNamedNative",
        query="SELECT * FROM todos t WHERE " +
                "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
                "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))",
        resultClass = Todo.class
)
@NamedQuery(name = "Todo.findBySearchTermNamed",
        query = "SELECT t FROM Todo t WHERE " +
                "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))"
)
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "modification_time")
    private LocalDateTime modificationTime;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Version
    private long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(LocalDateTime modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (version != todo.version) return false;
        if (id != null ? !id.equals(todo.id) : todo.id != null) return false;
        if (creationTime != null ? !creationTime.equals(todo.creationTime) : todo.creationTime != null) return false;
        if (description != null ? !description.equals(todo.description) : todo.description != null) return false;
        if (modificationTime != null ? !modificationTime.equals(todo.modificationTime) : todo.modificationTime != null)
            return false;
        return title != null ? title.equals(todo.title) : todo.title == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (modificationTime != null ? modificationTime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (int) (version ^ (version >>> 32));
        return result;
    }
}
