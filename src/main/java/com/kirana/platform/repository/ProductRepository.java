package com.kirana.platform.repository;
import java.util.List;

import com.kirana.platform.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByStoreId(Long storeId);
}
