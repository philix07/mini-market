package com.felix.basic_projects.mini_market.service;

import com.felix.basic_projects.mini_market.exception.sales_report.EmptySalesReportException;
import com.felix.basic_projects.mini_market.exception.sales_report.InvalidSalesReportDateRangeException;
import com.felix.basic_projects.mini_market.model.entity.*;
import com.felix.basic_projects.mini_market.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalesReportService {

  // Nanoseconds: A second can be subdivided into 1 billion (1,000,000,000) nanoseconds.
  // Nanoseconds range from 0 to 999,999,999. To represent the last possible moment in a second,
  // we need to set nanoseconds to 999,999,999 (just before the next second starts)...

  private final TransactionRepository transactionRepository;

  public SalesReportService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  public SalesReport generateCustommSalesReport(LocalDate startDate, LocalDate endDate) {
    LocalDateTime formattedStartDate = startDate.atStartOfDay();
    LocalDateTime formattedEndDate = endDate.atTime(23, 59, 59, 999_999_999);

    return getSalesReportByDateRange(formattedStartDate, formattedEndDate);
  }

  public SalesReport generateYearlyReport(int year) {
    LocalDateTime formattedStartDate = LocalDate.of(year, 1, 1).atStartOfDay();
    LocalDateTime formattedEndDate = LocalDate.of(year, 12, 31)
      .atTime(23, 59, 59, 999_999_999);

    return getSalesReportByDateRange(formattedStartDate, formattedEndDate);
  }

  public SalesReport generateMonthlyReport(int year, int month) {
    LocalDateTime formattedStartDate = YearMonth.of(year, month).atDay(1).atStartOfDay();
    LocalDateTime formattedEndDate = YearMonth.of(year, month).atEndOfMonth()
      .atTime(23, 59, 59, 999_999_999);

    return getSalesReportByDateRange(formattedStartDate, formattedEndDate);
  }

  public SalesReport generateDailyReport(int year, int month, int day) {
    LocalDateTime formattedStartDate = YearMonth.of(year, month).atDay(day).atStartOfDay();
    LocalDateTime formattedEndDate = YearMonth.of(year, month).atDay(day)
      .atTime(23, 59, 59, 999_999_999);

    return getSalesReportByDateRange(formattedStartDate, formattedEndDate);
  }


  private SalesReport getSalesReportByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
    List<Transaction> transactions = transactionRepository.findTransactionsByDate(startDate, endDate);

    if(transactions.isEmpty()) {
      throw new EmptySalesReportException("There is no sales between date " + startDate + " until " + endDate);
    } else if (startDate.isAfter(endDate)) {
      throw new InvalidSalesReportDateRangeException("The specified date range for the sales report is invalid.");
    }

    int totalTransaction = transactions.size();

    double totalRevenue = transactions.stream().mapToDouble(
      Transaction::getTotalPrice
    ).sum();

    // flatMap(): For each Transaction, it gets the list of TransactionItem objects and
    // flattens them into a single stream. This operation merges the lists.
    List<TransactionItem> transactionItems = transactions.stream()
      .flatMap(transaction -> transaction.getTransactionItems().stream())
      .toList();

    // Group all the TransactionItem into a single map to ease the next manipulation
    Map<Product, List<TransactionItem>> groupedTransactionItem = transactionItems.stream()
      .collect(Collectors.groupingBy(TransactionItem::getProduct));

    // Revert it back into a single list, but Each ProductTransactionSummary
    List<ProductTransactionSummary> summarizedTransactionItem = new ArrayList<>(groupedTransactionItem
      .entrySet()
      .stream().map(
        entry -> {
          return new ProductTransactionSummary(
            entry.getKey(),
            entry.getValue().stream().mapToInt(TransactionItem::getQuantity).sum(),
            entry.getValue().stream().mapToDouble(TransactionItem::getTotal).sum()
          );
        })
      .toList());

    // Sort the summarized product based on its product id
    summarizedTransactionItem.sort(
      Comparator.comparing(productSummary -> productSummary.getProduct().getId())
    );


    return new SalesReport(
      LocalDateTime.now(),
      totalRevenue,
      totalTransaction,
      summarizedTransactionItem
    );
  }
}
