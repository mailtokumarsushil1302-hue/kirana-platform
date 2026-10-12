package com.kirana.platform.repository;

import com.kirana.platform.model.Order;
import com.kirana.platform.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStoreId(Long storeId);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByStoreIdAndStatus(Long storeId, OrderStatus status);
}