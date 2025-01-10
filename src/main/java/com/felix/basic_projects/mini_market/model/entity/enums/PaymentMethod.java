package com.felix.basic_projects.mini_market.model.entity.enums;

public enum PaymentMethod {

  CASH("Cash"),
  E_WALLET("E-Wallet"),
  CREDIT_CARD("Credit Card"),
  BANK_TRANSFER("Bank Transfer");

  private final String paymentMethodName;

  PaymentMethod(String paymentMethodName) {
    this.paymentMethodName = paymentMethodName;
  }

  public String getPaymentMethodName() {
    return paymentMethodName;
  }

  public static PaymentMethod fromString(String categoryName) {
    for (PaymentMethod category : PaymentMethod.values()) {
      if (category.paymentMethodName.equalsIgnoreCase(categoryName)) {
        return category;
      }
    }
    throw new IllegalArgumentException("Invalid category: " + categoryName);
  }

}
