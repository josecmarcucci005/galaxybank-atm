package com.galaxybank.service;

import com.galaxybank.model.*;
import com.galaxybank.repository.AccountRepository;
import com.galaxybank.repository.AtmRepository;
import com.galaxybank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AtmRepository atmRepository;

    public Card findCardById(long cardId) {
        return cardRepository.findOne(cardId);
    }

    public Card findCardByIdAndPin(long cardId, String pin) {
        Card card = cardRepository.findOne(cardId);

        if (card != null && card.getSecurityCd().equals(pin)) {
            return card;
        }
        return null;
    }

    public Account findAccountByCardId(long cardId) {
        Card card = findCardById(cardId);

        if (card != null) {
            return card.getAccount();
        }
        return null;
    }

    public List<Transaction> findTransactionsByCardId(long cardId) {
        Card card = findCardById(cardId);

        if (card != null) {
            return card.getTransactions();
        }
        return null;
    }

    public ATM findATMById(long atmId) {
        return atmRepository.findOne(atmId);
    }

    public Customer saveTransaction(Transaction transaction) {
        Card card = transaction.getCard();
        Account account = card.getAccount();

        card.getTransactions().add(transaction);

        double balance = account.getBalance();
        if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
            account.setBalance(balance + transaction.getAmount());
        } else if (transaction.getTransactionType() == TransactionType.WITHDRAWN) {
            account.setBalance(balance - transaction.getAmount());
        }
        accountRepository.save(account);

        return account.getCustomer();
    }
}
