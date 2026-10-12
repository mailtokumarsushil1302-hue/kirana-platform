package com.kirana.platform.controller;
import com.kirana.platform.repository.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirana.platform.model.Product;
import com.kirana.platform.model.Store;
import com.kirana.platform.repository.StoreRepository;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    // Register a new store (Shopkeeper)
    @PostMapping("/register")
    public ResponseEntity<Store> registerStore(@RequestBody Store store) {
        return ResponseEntity.ok(storeRepository.save(store));
    }

    // Get all registered stores (Customer view)
    @GetMapping("/all")
    public ResponseEntity<List<Store>> getAllStores() {
        return ResponseEntity.ok(storeRepository.findAll());
    }

    // Add a product to a store (Shopkeeper)
    @PostMapping("/product/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }
    


    // Get product list of a specific store (Customer view)
    @GetMapping("/{storeId}/products")
    public ResponseEntity<List<Product>> getStoreProducts(@PathVariable Long storeId) {
        return ResponseEntity.ok(productRepository.findByStoreId(storeId));
    }
}