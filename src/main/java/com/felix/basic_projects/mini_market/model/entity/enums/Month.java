package com.felix.basic_projects.mini_market.model.entity.enums;

public enum Month {

  JANUARY(1, "January"),
  FEBRUARY(2, "February"),
  MARCH(3, "March"),
  APRIL(4, "April"),
  MAY(5, "May"),
  JUNE(6, "June"),
  JULY(7, "July"),
  AUGUST(8, "August"),
  SEPTEMBER(9, "September"),
  OCTOBER(10, "October"),
  NOVEMBER(11, "November"),
  DECEMBER(12, "December");

  private final int number;
  private final String name;

  Month(int number, String name) {
    this.number = number;
    this.name = name;
  }

  public int getNumber() {
    return number;
  }

  public String getName() {
    return name;
  }

  public static Month fromNumber(int number) {
    for (Month month : Month.values()) {
      if (month.number == number) {
        return month;
      }
    }
    throw new IllegalArgumentException("Invalid month number: " + number);
  }

  public static Month fromName(String name) {
    for (Month month : Month.values()) {
      if (month.name.equalsIgnoreCase(name)) {
        return month;
      }
    }
    throw new IllegalArgumentException("Invalid month name: " + name);
  }

}
