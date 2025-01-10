package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.model.dto.request.ActivityLogRequestDTO;
import com.felix.basic_projects.mini_market.model.dto.response.ActivityLogResponseDTO;
import com.felix.basic_projects.mini_market.model.entity.ActivityLog;
import com.felix.basic_projects.mini_market.service.ActivityLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("v1")
public class ActivityLogController {

  @Autowired
  ActivityLogService logService;

  @GetMapping("logs")
  public ResponseEntity<List<ActivityLog>> retrieveAllActivityLog() {
    List<ActivityLog> logs = logService.retrieveAllActivityLog();

    return ResponseEntity.ok(logs);
  }

  @GetMapping("logs/users/{uid}")
  public ResponseEntity<List<ActivityLog>> findAllActivityLogByUserId(@PathVariable Long uid) {
    List<ActivityLog> logs = logService.retrieveAllActivityLogByUserId(uid);

    return ResponseEntity.ok(logs);
  }

  @GetMapping("logs/resources/{resource}")
  public ResponseEntity<List<ActivityLog>> findAllActivityLogByResource(@PathVariable String resource) {
    List<ActivityLog> logs = logService.retrieveAllActivityLogByResource(resource);

    return ResponseEntity.ok(logs);
  }

  @GetMapping("logs/dates")
  public ResponseEntity<List<ActivityLog>> findAllActivityLogByResource(
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
  ) {
    List<ActivityLog> logs = logService.retrieveAllActivityLogByDateRange(startDate, endDate);

    return ResponseEntity.ok(logs);
  }

  @PostMapping("logs")
  public ResponseEntity<ActivityLogResponseDTO> saveActivityLog(@RequestBody @Valid ActivityLogRequestDTO request) {
    return ResponseEntity.ok(logService.saveActivityLog(request));
  }
}
