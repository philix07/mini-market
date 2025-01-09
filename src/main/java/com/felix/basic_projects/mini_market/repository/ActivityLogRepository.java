package com.felix.basic_projects.mini_market.repository;

import com.felix.basic_projects.mini_market.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
  List<ActivityLog> findAllByName(String name);

  // custom JPQL
  // missing queries
  @Query
  List<ActivityLog> findAllByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
