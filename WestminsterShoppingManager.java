import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;

public class WestminsterShoppingManager {
    private List<Product> availableProducts = new ArrayList<Product>();
    private List<Product> clothingProducts = new ArrayList<Product>();
    private List<Product> ElectronicsProuct = new ArrayList<Product>();
    private static final String fileName = "Available_Products.dat";

    public void addClothing(String productId, String productName, int availableItems, double price, String size,
            String color) {
        if (availableProducts.size() < 50) {
            Product product = new Clothing(productId, productName, availableItems, price, size, color);
            availableProducts.add(product);
            clothingProducts.add(product);
        } else {
            System.out.println("Maximum product count exceeded.");
        }
    }

    public void addElectronics(String productId, String productName, int availableItems, double price, String brand,
            int warrantyPeriod) {
        if (availableProducts.size() < 50) {
            Product product = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
            availableProducts.add(product);
            ElectronicsProuct.add(product);
        } else {
            System.out.println("Maximum product count exceeded.");
        }

    }

    public Product getProductById(String productID) {
        Iterator<Product> iterator = availableProducts.iterator();
        Product product = null;
        while (iterator.hasNext()) {
            product = iterator.next();
            if (product.getProductId().equals(productID)) {
                break;
            }
        }
        return product;
    }

    public List<Product> getProductsByType(String category) {
        if (category == "Clothes") {
            return clothingProducts;
        } else if (category == "Electronics") {
            return ElectronicsProuct;
        } else {
            return availableProducts;
        }
    }

    public void removeProduct(String productID) {
        Product product = getProductById(productID);
        if (product != null) {
            String category = product.getClass().getName();
            availableProducts.remove(product);
            if (category == "Clothing") {
                clothingProducts.remove(product);
            } else {
                ElectronicsProuct.remove(product);
            }
            System.out.println(category + " product has been deleted and the " + availableProducts.size()
                    + " products left in the system");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void printDetails() {
        Collections.sort(availableProducts);

        for (Product product : availableProducts) {
            product.displayDetails();
        }

    }

    public void saveProductList() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(availableProducts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProducts() {
        File file = new File(fileName);

        if (file.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
                availableProducts = (List<Product>) inputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void startGUI(ShoppingCart cart) {
        Thread guiThread = new Thread(() -> openGUI(cart));
        guiThread.start();
        try {
            guiThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void openGUI(ShoppingCart cart) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createANdShowGUI(cart);
            }
        });
    }

    public void createANdShowGUI(ShoppingCart cart) {
        ShopingGUI gui = new ShopingGUI(this, cart);
        gui.setVisible(true);
    }

}
