package com.example.model.hibernateInheritance.joined;

import javax.persistence.*;

@Entity
@Table(name = "PERSON_3")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person3 {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "PERSON_ID")
    private Long personId;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    public Person3() {

    }
    public Person3(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "Person1{" +
                "personId=" + personId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person3 person1 = (Person3) o;

        if (personId != null ? !personId.equals(person1.personId) : person1.personId != null) return false;
        if (firstname != null ? !firstname.equals(person1.firstname) : person1.firstname != null) return false;
        return lastname != null ? lastname.equals(person1.lastname) : person1.lastname == null;
    }

    @Override
    public int hashCode() {
        int result = personId != null ? personId.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }
}
