package com.kirana.platform.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeName;
    private String ownerName;
    private String phone;
    private String address;

    public Store() {}

    public Store(String storeName, String ownerName, String phone, String address) {
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() { return id; }
    public String getStoreName() { return storeName; }
    public String getOwnerName() { return ownerName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}