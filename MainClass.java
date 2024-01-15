import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        WestminsterShoppingManager shoppingSystem = new WestminsterShoppingManager();
        shoppingSystem.loadProducts();
        System.out.println(
                "Enter \"1\" to add a new clothing product.\nEnter \"2\" to add a new electronic item.\nEnter \"3\" to delete a product.\nEnter \"4\" to print all available products.\nEnter \"5\" to go shopping.\nEnter \"6\" to quit.");

        // Create a single Scanner instance for the entire loop
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                int action = scanner.nextInt();
                scanner.nextLine();

                switch (action) {
                    case 1:
                        System.out.println("Enter attributes for clothing in respective order.");
                        String[] attributes1 = scanner.nextLine().split(" ");
                        shoppingSystem.addClothing(attributes1[0], attributes1[1], Integer.parseInt(attributes1[2]),
                                Double.parseDouble(attributes1[3]), attributes1[4], attributes1[5]);
                        break;

                    case 2:
                        System.out.print("Enter attributes for electronic item in respective order: ");
                        String[] attributes2 = scanner.nextLine().split(" ");
                        shoppingSystem.addElectronics(attributes2[0], attributes2[1], Integer.parseInt(attributes2[2]),
                                Double.parseDouble(attributes2[3]), attributes2[4],
                                Integer.parseInt(attributes2[5]));
                        break;

                    case 3:
                        System.out.print("Enter the product ID to delete: ");
                        String ID = scanner.nextLine();
                        shoppingSystem.removeProduct(ID);
                        break;

                    case 4:
                        shoppingSystem.printDetails();
                        break;

                    case 5:
                        System.out.print("Please enter User Name: ");
                        String userName = scanner.nextLine();
                        User user = new User(userName);
                        ShoppingCart cart = new ShoppingCart(user);
                        shoppingSystem.startGUI(cart);
                        break;
                }

                if (action == 6) {
                    shoppingSystem.saveProductList();
                    System.out.println("System is shutting down.");
                    break;
                }
            }
            shoppingSystem.saveProductList();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
