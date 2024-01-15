import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> productList;
    private User user;
    private int totalPrice = 0;

    public ShoppingCart(User user) {
        this.user = user;
        this.productList = new ArrayList<>();
    }

    public void addToShoppingCart(Product product) {
        productList.add(product);
        totalPrice += product.getPrice();
    }

    public void removeProduct(Product product) {
        if (productList.contains(product)) {
            productList.remove(product);
        } else {
            System.out.println("Item is not in cart");
        }
    }

    public int calculateTotalPrice() {
        return totalPrice;
    }

    public List<Product> getShoppingCart() {
        return productList;
    }

}
