
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ShopingGUI extends JFrame {
    private WestminsterShoppingManager shoppingManager;
    private ShoppingCart cart;
    private JComboBox<String> productTypeComboBox;
    private JTable productTable;
    private JTextArea productDetailsTextArea;
    private JButton addToCartButton;
    private JButton viewShoppingCartButton;

    public ShopingGUI(WestminsterShoppingManager shoppingManager, ShoppingCart cart) {
        this.shoppingManager = shoppingManager;
        this.cart = cart;

        setTitle("Westminister Shopping Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents();

        setLayout(new BorderLayout());

        add(createProductSelectionPanel(), BorderLayout.NORTH);
        add(createProductTablePanel(), BorderLayout.CENTER);
        add(createProductDetailsPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void createComponents() {
        productTypeComboBox = new JComboBox<>(new String[] { "All", "Electronics", "Clothes" });
        productTable = new JTable();
        productDetailsTextArea = new JTextArea();
        addToCartButton = new JButton("Add to Cart");
        viewShoppingCartButton = new JButton("Shopping Cart");

        productTypeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProductTable();
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToShoppingCart();
            }
        });

        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Check if the selection is not adjusting (to avoid firing events twice)
                if (!e.getValueIsAdjusting()) {
                    // Get the selected row
                    int selectedRow = productTable.getSelectedRow();

                    // Display product information in the text area
                    displayProductInfo(selectedRow);
                }
            }
        });

        viewShoppingCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewShoppingCart();
            }
        });
    }

    private JPanel createProductSelectionPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Select Category type : "));
        panel.add(productTypeComboBox);
        panel.add(addToCartButton);
        panel.add(viewShoppingCartButton);
        return panel;
    }

    private JPanel createProductTablePanel() {
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(productTable);
        panel.add(scrollPane);
        return panel;
    }

    private JPanel createProductDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout()); // Use BorderLayout for the panel
        JScrollPane scrollpane = new JScrollPane(productDetailsTextArea);
        panel.add(scrollpane, BorderLayout.CENTER); // Add text area to the center of the panel
        return panel;
    }

    private void updateProductTable() {
        String selectedType = (String) productTypeComboBox.getSelectedItem();
        List<Product> products = shoppingManager.getProductsByType(selectedType);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Available Items");
        model.addColumn("Price");

        for (Product product : products) {
            Object[] row = { product.getProductId(), product.getProductName(), product.getAvailableItems(),
                    product.getPrice() };
            model.addRow(row);
        }

        productTable.setModel(model);

        colourTheRows();

    }

    private void colourTheRows() {
        for (int i = 0; i < productTable.getRowCount(); i++) {
            int availableItems = (int) productTable.getValueAt(i, 2);
            if (availableItems < 3) {
                productTable.getCellRenderer(i, 0).getTableCellRendererComponent(productTable, null, true, true, i, 0)
                        .setBackground(Color.red);
            } else {
                productTable.getCellRenderer(i, 0).getTableCellRendererComponent(productTable, null, true, true, i, 0)
                        .setBackground(Color.green);
            }
        }
    }

    private void addToShoppingCart() {
        int selectedRow = productTable.getSelectedRow();
        String productID = (String) productTable.getValueAt(selectedRow, 0);
        Product selectedProduct = shoppingManager.getProductById(productID);
        cart.addToShoppingCart(selectedProduct);
    }

    private void displayProductInfo(int selectedRow) {
        Product seletedProduct = shoppingManager.getProductById((String) productTable.getValueAt(selectedRow, 0));
        StringBuilder cartDetails = new StringBuilder("Selected Product - Details :\n");

        cartDetails.append("Product ID : ").append(seletedProduct.getProductId()).append(" \nProduct Name : ")
                .append(seletedProduct.getProductName()).append("\nItems Available : ")
                .append(seletedProduct.getAvailableItems()).append("\nPrice : ").append(seletedProduct.getPrice());

        productDetailsTextArea.setText(cartDetails.toString());

    }

    private void viewShoppingCart() {

    }

}
