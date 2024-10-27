package question_2_1_product_inventory_management;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        inventory.addProduct(new Product("Laptop", 999.99, 5));
        inventory.addProduct(new Product("Smartphone", 499.99, 10));
        inventory.addProduct(new Product("Tablet", 299.99, 0));
        inventory.addProduct(new Product("Smartwatch", 199.99, 3));

        System.out.println("Total Inventory Value: " + inventory.calculateTotalInventoryValue());

        System.out.println("Most Expensive Product: " + inventory.findMostExpensiveProduct());

        System.out.println("Is 'Headphones' in stock? " + inventory.isProductInStock("Headphones"));

        System.out.println("Products sorted by price descending:");
        for (Product product : inventory.sortProductsBy("price", false)) {
            System.out.println(product);
        }

        System.out.println("Products sorted by quantity ascending:");
        for (Product product : inventory.sortProductsBy("quantity", true)) {
            System.out.println(product);
        }
    }
}
