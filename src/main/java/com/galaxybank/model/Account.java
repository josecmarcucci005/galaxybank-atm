package com.galaxybank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", unique = true, nullable = false)
    long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Card> cards = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    @JsonBackReference
    Customer customer;

    double balance;

    @Enumerated(EnumType.STRING)
    AccountType accountType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "account {" + "\r\n" +
                "id=" + id + "\r\n" +
                ", cards=" + cards + "\r\n" +
                ", customerId=" + customer.getId() + "\r\n" +
                ", balance=" + balance + "\r\n" +
                ", accountType=" + accountType + "\r\n" +
                '}' + "\r\n";
    }
}
