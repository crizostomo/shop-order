package com.example.shoporder.persistence.repository;

import com.example.shoporder.persistence.model.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopOrderRepository  extends JpaRepository<ShopOrder, Long> {
}
