package com.felix.basic_projects.mini_market.controller;

import com.felix.basic_projects.mini_market.model.SalesReport;
import com.felix.basic_projects.mini_market.model.enums.SalesReportType;
import com.felix.basic_projects.mini_market.service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1")
public class SalesReportController {

  @Autowired
  SalesReportService salesReportService;

  @GetMapping("/salesReports/custom")
  public ResponseEntity<SalesReport> generateCustomSalesReport(
    @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
    @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
  ) {
    SalesReport salesReport = salesReportService.generateCustommSalesReport(startDate, endDate);
    return ResponseEntity.ok(salesReport);
  }

  @GetMapping("/salesReports/yearly")
  public ResponseEntity<SalesReport> generateYearlyReport(@RequestParam int year) {
    SalesReport salesReport = salesReportService.generateYearlyReport(year);
    return ResponseEntity.ok(salesReport);
  }

  @GetMapping("/salesReports/monthly")
  public ResponseEntity<SalesReport> generateMonthlyReport(@RequestParam int year, @RequestParam int month) {
    SalesReport salesReport = salesReportService.generateMonthlyReport(year, month);
    return ResponseEntity.ok(salesReport);
  }

  @GetMapping("/salesReports/daily")
  public ResponseEntity<SalesReport> generateDailyReport(
    @RequestParam int year,
    @RequestParam int month,
    @RequestParam int day
  ) {
    SalesReport salesReport = salesReportService.generateDailyReport(year, month, day);
    return ResponseEntity.ok(salesReport);
  }
}
