package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.model.dto.request.CreateActivityLogRequestDTO;
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
  public ResponseEntity<List<ActivityLogResponseDTO>> retrieveAllActivityLog() {
    return ResponseEntity.ok(logService.retrieveAllActivityLog());
  }

  @GetMapping("logs/users/{uid}")
  public ResponseEntity<List<ActivityLogResponseDTO>> findAllActivityLogByUserId(@PathVariable Long uid) {
    return ResponseEntity.ok(logService.retrieveAllActivityLogByUserId(uid));
  }

  @GetMapping("logs/resources/{resource}")
  public ResponseEntity<List<ActivityLogResponseDTO>> findAllActivityLogByResource(@PathVariable String resource) {
    return ResponseEntity.ok(logService.retrieveAllActivityLogByResource(resource));
  }

  @GetMapping("logs/dates")
  public ResponseEntity<List<ActivityLogResponseDTO>> findAllActivityLogByResource(
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
  ) {
    return ResponseEntity.ok(logService.retrieveAllActivityLogByDateRange(startDate, endDate));
  }

//  @PostMapping("logs")
//  public ResponseEntity<ActivityLogResponseDTO> saveActivityLog(@RequestBody @Valid CreateActivityLogRequestDTO request) {
//    return ResponseEntity.ok(logService.saveActivityLog(request));
//  }
}
