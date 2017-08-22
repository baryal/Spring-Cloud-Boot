package com.example.model.hibernateInheritance.joined;

import com.example.model.hibernateInheritance.tableperclass.Person1;

import javax.persistence.*;

@Entity
@Table(name = "OWNER_3")
public class Owner3 extends Person3 {

    @Column(name="stocks")
    private Long stocks;

    @Column(name="partnership_stake")
    private Long partnershipStake;

    public Owner3() {
    }

    public Owner3(String firstname, String lastname, Long stocks, Long partnershipStake) {

        super(firstname, lastname);

        this.stocks = stocks;
        this.partnershipStake = partnershipStake;
    }

    public Long getStocks() {

        return stocks;
    }

    public void setStocks(Long stocks) {
        this.stocks = stocks;
    }

    public Long getPartnershipStake() {
        return partnershipStake;
    }

    public void setPartnershipStake(Long partnershipStake) {
        this.partnershipStake = partnershipStake;
    }

    @Override
    public String toString() {
        return "Owner1{" +
                "stocks=" + stocks +
                ", partnershipStake=" + partnershipStake +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Owner3 owner1 = (Owner3) o;

        if (stocks != null ? !stocks.equals(owner1.stocks) : owner1.stocks != null) return false;
        return partnershipStake != null ? partnershipStake.equals(owner1.partnershipStake) : owner1.partnershipStake == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (stocks != null ? stocks.hashCode() : 0);
        result = 31 * result + (partnershipStake != null ? partnershipStake.hashCode() : 0);
        return result;
    }

}
