package com.galaxybank.service;

import com.galaxybank.model.ATM;
import com.galaxybank.model.AdminUser;
import com.galaxybank.repository.AdminRepository;

import com.galaxybank.repository.AtmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AtmRepository atmRepository;

    public AdminUser getAdminUser(String email, String pwd) {
        List<AdminUser> users = adminRepository.findByEmailAndPassword(email, pwd);

        if (users != null && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    public ATM findATMById(long atmId) {
        return atmRepository.findOne(atmId);
    }

    public ATM updateBalanceATM(long atmId, double balance) {
        ATM atm = findATMById(atmId);

        if (atm != null) {
            atm.setBalance(balance);

            atmRepository.save(atm);
        }
        return atm;
    }
}
