package com.galaxybank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", unique = true, nullable = false)
    long id;

    String securityCd;
    Date expiringDate;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    @JsonBackReference
    Account account;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "card", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Transaction> transactions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecurityCd() {
        return securityCd;
    }

    public void setSecurityCd(String securityCd) {
        this.securityCd = securityCd;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "card {" + "\r\n" +
                "id=" + id + "\r\n" +
                ", securityCd='" + securityCd + '\'' + "\r\n" +
                ", expiringDate=" + expiringDate + "\r\n" +
                ", accountId=" + account.getId() + "\r\n" +
                ", transactions=" + transactions + "\r\n" +
                '}' + "\r\n";
    }
}
