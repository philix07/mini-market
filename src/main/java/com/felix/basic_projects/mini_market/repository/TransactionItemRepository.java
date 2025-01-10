package com.felix.basic_projects.mini_market.repository;

import com.felix.basic_projects.mini_market.model.entity.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
}
