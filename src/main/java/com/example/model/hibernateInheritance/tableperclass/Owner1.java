package com.example.model.hibernateInheritance.tableperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "OWNER_1")
@AttributeOverrides({
        @AttributeOverride(name="firstname", column=@Column(name="FIRSTNAME")),
        @AttributeOverride(name="lastname", column=@Column(name="LASTNAME"))
})
@Data
@EqualsAndHashCode(callSuper=false)
public class Owner1 extends Person1 {

    @Column(name="stocks")
    private Long stocks;

    @Column(name="partnership_stake")
    private Long partnershipStake;

    public Owner1() {
    }

    public Owner1(String firstname, String lastname, Long stocks, Long partnershipStake) {

        super(firstname, lastname);

        this.stocks = stocks;
        this.partnershipStake = partnershipStake;
    }
}
