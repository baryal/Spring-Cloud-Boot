package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue
    //@Column(name = "address_id")
    @Column(name = "user_id")
    private Long id;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @OneToOne
    //@JoinColumn(name = "user_id")
    @PrimaryKeyJoinColumn
    //@MapsId
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", addressType='" + addressType + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
