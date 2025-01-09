package com.felix.basic_projects.mini_market.exception.sales_report;

// Thrown when the provided date range for generating a sales report is invalid.
public class InvalidSalesReportDateRangeException extends RuntimeException {
  public InvalidSalesReportDateRangeException(String message) {
    super(message);
  }
}
