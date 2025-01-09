package com.felix.basic_projects.mini_market.model;

public class ProductTransactionSummary {

  Product product;
  int quantitySold;
  double totalPrice;

  public ProductTransactionSummary(Product product, int quantitySold, double totalPrice) {
    this.product = product;
    this.quantitySold = quantitySold;
    this.totalPrice = totalPrice;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public int getQuantitySold() {
    return quantitySold;
  }

  public void setQuantitySold(int quantitySold) {
    this.quantitySold = quantitySold;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  @Override
  public String toString() {
    return "ProductTransactionSummary{" +
      "product=" + product +
      ", quantitySold=" + quantitySold +
      ", totalPrice=" + totalPrice +
      '}';
  }
}
