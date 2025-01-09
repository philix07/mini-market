package com.felix.basic_projects.mini_market.exception.sales_report;

// Thrown when no data is available for the requested report.
public class EmptySalesReportException extends RuntimeException {
  public EmptySalesReportException(String message) {
    super(message);
  }
}
