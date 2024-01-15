import java.io.Serializable;

abstract class Product implements Comparable<Product>, Serializable {
    private String productId;
    private String productName;
    private int availableItems;
    private double price;

    // constructor for the abstract class
    public Product(String productId,
            String productName,
            int availableItems,
            double price) {

        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    // Getters and setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public abstract void displayDetails();

    public int compareTo(Product otherProduct) {
        return this.productId.compareTo(otherProduct.productId);
    }

}
