package com.galaxybank.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id", unique = true, nullable = false)
    long id;

    String name;
    String swiftCode;
    String address;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    List<ATM> atmList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bank")
    @JsonManagedReference
    List<Customer> customers = new ArrayList<>();

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

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ATM> getAtmList() {
        return atmList;
    }

    public void setAtmList(List<ATM> atmList) {
        this.atmList = atmList;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "bank {" + "\r\n" +
                "id=" + id + "\r\n" +
                ", name='" + name + '\'' + "\r\n" +
                ", swiftCode='" + swiftCode + '\'' + "\r\n" +
                ", address='" + address + '\'' + "\r\n" +
                ", atmList=" + atmList + "\r\n" +
                ", customers=" + customers + "\r\n" +
                '}' + "\r\n";
    }
}
