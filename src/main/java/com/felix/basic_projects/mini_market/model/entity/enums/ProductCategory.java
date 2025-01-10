package com.felix.basic_projects.mini_market.model.entity.enums;

public enum ProductCategory {

  SNACK("Snack"),
  BEVERAGE("Beverage"),
  GROCERY("Grocery"),
  DAIRY("Dairy"),
  FROZEN("Frozen"),
  FRUITS("Fruits"),
  VEGETABLES("Vegetables"),
  HOUSEHOLD("Household"),
  BEAUTY("Beauty"),
  HEALTH("Health");

  private final String categoryName;

  ProductCategory(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public static ProductCategory fromString(String categoryName) {
    for (ProductCategory category : ProductCategory.values()) {
      if (category.categoryName.equalsIgnoreCase(categoryName)) {
        return category;
      }
    }
    throw new IllegalArgumentException("Invalid category: " + categoryName);
  }

}
