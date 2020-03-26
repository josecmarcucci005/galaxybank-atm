package com.galaxybank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", unique = true, nullable = false)
    long id;

    String name;
    String address;
    String phone;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Account> accounts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="bank_id", nullable=false)
    @JsonBackReference
    Bank bank;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "customer {" + "\r\n" +
                "id=" + id + "\r\n" +
                ", name='" + name + '\'' + "\r\n" +
                ", address='" + address + '\'' + "\r\n" +
                ", phone='" + phone + '\'' + "\r\n" +
                ", accounts=" + accounts + "\r\n" +
                ", bankId=" + bank.getId() + "\r\n" +
                '}' + "\r\n";
    }

    public String toJson() {



        return null;
    }
}
