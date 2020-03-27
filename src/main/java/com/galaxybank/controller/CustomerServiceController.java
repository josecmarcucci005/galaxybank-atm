package com.galaxybank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galaxybank.model.*;
import com.galaxybank.service.AdminService;
import com.galaxybank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/galaxybank")
public class CustomerServiceController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public String galaxybankatm(Model model) {
        return "galaxybankatm";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }

    @RequestMapping(path = "/validateCard", method = { RequestMethod.GET, RequestMethod.POST },
            produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String validateCardWithPin(@RequestParam(value="card") Long card,
                               @RequestParam(value="pin") String pin) {

        Card resultCard = customerService.findCardByIdAndPin(card, pin);

        Map<String, Object> map = new HashMap<>();
        if (resultCard != null) {
            Account account = resultCard.getAccount();
            Customer customer = account.getCustomer();

            map.put("result", "OK");
            map.put("data", customer);
        } else {
            map.put("result", "ERROR");
            map.put("errMsg", "The card or security pin are not valid! Please try again or contact your bank.");
        }
        return getJson(map);
    }

    @RequestMapping(path = "/saveTransaction", method = { RequestMethod.GET, RequestMethod.POST },
            produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String saveTransaction(@RequestParam(value="card") Long card,
                           @RequestParam(value="type") String type,
                           @RequestParam(value="amount") Double amount,
                           @RequestParam(value="atmId") Long atmId) {

        String errMsg = null;
        Customer customer = null;
        Map<String, Object> map = new HashMap<>();
        try {
            Card resultCard = customerService.findCardById(card);

            if (resultCard == null) {
                throw new Exception("The card or security pin are not valid! " +
                        "Please try again or contact your bank.");
            }

            ATM atm = customerService.findATMById(atmId);

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setAtm(atm);
            transaction.setTransactionType(TransactionType.valueOf(type));
            transaction.setTransactionDate(new Date());
            transaction.setCard(resultCard);

            customer = customerService.saveTransaction(transaction);
        }catch(Exception e) {
            errMsg = e.getMessage();
        }

        if (errMsg == null) {
            map.put("result", "OK");
            map.put("data", customer);
        } else {
            map.put("result", "ERROR");
            map.put("errMsg", errMsg);
        }
        return getJson(map);
    }

    @RequestMapping(path = "/loginAdmin", method = { RequestMethod.GET, RequestMethod.POST },
            produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String loginAdmin(@RequestParam(value="email") String email,
                           @RequestParam(value="pwd") String pwd) {

        AdminUser user = adminService.getAdminUser(email, pwd);

        Map<String, Object> map = new HashMap<>();
        if (user != null) {
            map.put("result", "OK");
            map.put("data", user);
        } else {
            map.put("result", "ERROR");
            map.put("errMsg", "The email or password are not valid! Please try again or contact IT support.");
        }
        return getJson(map);
    }

    @RequestMapping(path = "/getAtmInfo", method = { RequestMethod.GET, RequestMethod.POST },
            produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String getAtmInfo(@RequestParam(value="atmId") Long atmId) {

        ATM atm = adminService.findATMById(atmId);

        Map<String, Object> map = new HashMap<>();
        if (atm != null) {
            map.put("result", "OK");
            map.put("data", atm);
        } else {
            map.put("result", "ERROR");
            map.put("errMsg", "Is not possible to find the ATM's information right now! " +
                    "Please try again or contact IT support.");
        }
        return getJson(map);
    }

    @RequestMapping(path = "/updateAtmBalance", method = { RequestMethod.GET, RequestMethod.POST },
            produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String updateAtmBalance(@RequestParam(value="atmId") Long atmId,
                            @RequestParam(value="balance") double balance) {

        ATM atm = adminService.updateBalanceATM(atmId, balance);

        Map<String, Object> map = new HashMap<>();
        if (atm != null) {
            map.put("result", "OK");
            map.put("data", atm);
        } else {
            map.put("result", "ERROR");
            map.put("errMsg", "The balance update for the ATM failed! " +
                    "Please try again or contact IT support.");
        }
        return getJson(map);
    }

    private String getJson(Map<String, Object> map) {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = "{}";
        try {
            json = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
