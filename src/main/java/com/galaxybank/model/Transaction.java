package com.galaxybank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id", unique = true, nullable = false)
    long id;

    @ManyToOne
    @JoinColumn(name="card_id", nullable=false)
    @JsonBackReference
    Card card;

    Date transactionDate;

    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    double amount = 0;

    @ManyToOne
    @JoinColumn(name="atm_id", nullable=false)
    @JsonBackReference
    ATM atm;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public ATM getAtm() {
        return atm;
    }

    public void setAtm(ATM atm) {
        this.atm = atm;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "transaction {" + "\r\n" +
                "id=" + id + "\r\n" +
                ", cardId=" + card.getId() + "\r\n" +
                ", transactionDate=" + transactionDate + "\r\n" +
                ", transactionType=" + transactionType + "\r\n" +
                ", amount=" + amount + "\r\n" +
                ", atmId=" + atm.getId() + "\r\n" +
                '}' + "\r\n";
    }
}
