package com.felix.basic_projects.mini_market.model.entity.enums;

public enum SalesReportType {

  CUSTOM_REPORT("Custom Report"),
  MONTHLY_REPORT("Monthly Report"),
  YEARLY_REPORT("Yearly Report"),
  DAILY_REPORT("Daily Report"),
  WEEKLY_REPORT("Weekly Report");

  private final String description;

  SalesReportType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
