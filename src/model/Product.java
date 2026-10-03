package model;

import java.math.BigDecimal;

/** Holds the data of one product (one row of the products table). */
public class Product {
    private int id;
    private String productName;
    private String description;
    private int quantity;
    private BigDecimal price;

    public Product(int id, String productName, String description, int quantity, BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    /** Used when adding a new product (the database creates the id). */
    public Product(String productName, String description, int quantity, BigDecimal price) {
        this(0, productName, description, quantity, price);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getProductName() { return productName; }
    public String getDescription() { return description; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
}
