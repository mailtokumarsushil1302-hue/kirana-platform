package com.kirana.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kirana.platform.exception.ResourceNotFoundException;
import com.kirana.platform.model.Order;
import com.kirana.platform.model.OrderItem;
import com.kirana.platform.model.OrderStatus;
import com.kirana.platform.model.Product;
import com.kirana.platform.repository.OrderRepository;
import com.kirana.platform.repository.ProductRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order placeOrder(Order order) {
        // 1. Validate stock and deduct quantity for each item
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + item.getProductId()));

                if (product.getQuantity() < item.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName() 
                            + ". Available: " + product.getQuantity() + ", Requested: " + item.getQuantity());
                }

                // Deduct quantity from inventory
                product.setQuantity(product.getQuantity() - item.getQuantity());
                productRepository.save(product);
            }
        }

        // 2. Set status and save order
        order.setStatus(OrderStatus.PLACED);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersForStore(Long storeId) {
        return orderRepository.findByStoreId(storeId);
    }

    public List<Order> getOrdersForCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }
}