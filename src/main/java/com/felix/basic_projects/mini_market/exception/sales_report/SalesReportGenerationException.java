package com.felix.basic_projects.mini_market.exception.sales_report;

// Thrown when a specific sales report is not found.
public class SalesReportGenerationException extends RuntimeException {
  public SalesReportGenerationException(String message) {
    super(message);
  }
}
