package com.kirana.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kirana.platform.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}