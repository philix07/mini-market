package com.felix.basic_projects.mini_market.model;

import java.time.LocalDateTime;
import java.util.List;

public class SalesReport {

  private LocalDateTime reportDate;
  private double totalRevenue;
  private int totalTransactions;
  private List<ProductTransactionSummary> productTransactionSummaries; // Can we use groupBy function?

  public SalesReport() {}

  public SalesReport(LocalDateTime reportDate, double totalRevenue, int totalTransactions, List<ProductTransactionSummary> productTransactionSummaries) {
    this.reportDate = reportDate;
    this.totalRevenue = totalRevenue;
    this.totalTransactions = totalTransactions;
    this.productTransactionSummaries = productTransactionSummaries;
  }

  public SalesReport(double totalRevenue, int totalTransactions, List<ProductTransactionSummary> productTransactionSummaries) {
    this.reportDate = LocalDateTime.now();
    this.totalRevenue = totalRevenue;
    this.totalTransactions = totalTransactions;
    this.productTransactionSummaries = productTransactionSummaries;
  }

  public LocalDateTime getReportDate() {
    return reportDate;
  }

  public void setReportDate(LocalDateTime reportDate) {
    this.reportDate = reportDate;
  }

  public double getTotalRevenue() {
    return totalRevenue;
  }

  public void setTotalRevenue(double totalRevenue) {
    this.totalRevenue = totalRevenue;
  }

  public int getTotalTransactions() {
    return totalTransactions;
  }

  public void setTotalTransactions(int totalTransactions) {
    this.totalTransactions = totalTransactions;
  }

  public List<ProductTransactionSummary> getProductTransactionSummaries() {
    return productTransactionSummaries;
  }

  public void setProductTransactionSummaries(List<ProductTransactionSummary> productTransactionSummaries) {
    this.productTransactionSummaries = productTransactionSummaries;
  }

  @Override
  public String toString() {
    return "SalesReport{" +
      "reportDate=" + reportDate +
      ", totalRevenue=" + totalRevenue +
      ", totalTransactions=" + totalTransactions +
      ", productTransactionSummaries=" + productTransactionSummaries +
      '}';
  }
}
