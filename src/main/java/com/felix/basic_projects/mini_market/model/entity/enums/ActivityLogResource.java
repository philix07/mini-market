package com.felix.basic_projects.mini_market.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ActivityLogResource {
  TRANSACTION("transaction"),
  PRODUCT("product"),
  CUSTOMER("customer"),
  STOCK_ENTRY("stock_entry"),
  USER("user"),
  SALES_REPORT("sales_report");

  private final String value;

  public static ActivityLogResource fromString(String resource) {
    for (ActivityLogResource category : ActivityLogResource.values()) {
      if (category.value.equalsIgnoreCase(resource)) {
        return category;
      }
    }
    throw new IllegalArgumentException(
      "Invalid resource name: " + resource + ", available value " + Arrays.toString(ActivityLogResource.values())
    );
  }

}
