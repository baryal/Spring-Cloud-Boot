package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user")
@Data
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "gender")
    private String gender;

    @Column(name = "consent")
    private String consent;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    /*@Lob
    @Column(name="user_photo", nullable=false, columnDefinition="mediumblob")
    @JsonIgnore
    private byte[] image;*/

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
            optional = false)
    private Address address;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", consent='" + consent + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
