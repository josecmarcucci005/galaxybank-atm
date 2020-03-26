package com.galaxybank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "atm")
public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atm_id", unique = true, nullable = false)
    long id;

    String location;

    @ManyToOne
    @JoinColumn(name="bank_id", nullable=false)
    @JsonBackReference
    Bank bank;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "atm")
    @JsonManagedReference
    List<Transaction> transactions = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "atm {" + "\r\n" +
                "id=" + id + "\r\n" +
                ", location='" + location + '\'' + "\r\n" +
                ", bankId=" + bank.getId() + "\r\n" +
                ", transactions=" + transactions + "\r\n" +
                '}' + "\r\n";
    }
}
