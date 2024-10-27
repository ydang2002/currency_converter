package question_2_1_product_inventory_management;

import java.util.ArrayList;
import java.util.List;

class Inventory {
//    List<Product> products = new ArrayList<>();

    private final List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    // Apply Linear Traversal algorithm to calculate total inventory value
    // Complexity O(n)
    public double calculateTotalInventoryValue() {
        double totalValue = 0;
        for (Product product : products) {
            totalValue += product.getTotalValue();
        }
        return Math.round(totalValue * 100.0) / 100.0;
    }

    // Apply Linear Traversal algorithm to find the most expensive product
    // Complexity O(n)
    public String findMostExpensiveProduct() {
        if (products.isEmpty()) {
            return "No Products";
        }
        Product mostExpensive = products.get(0);
        for (Product product : products) {
            if (product.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = product;
            }
        }
        return mostExpensive.getName();
    }

    // Apply Linear Traversal algorithm to check if product is in stock
    public boolean isProductInStock(String productName) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName) && product.getQuantity() > 0) {
                return true;
            }
        }
        return false;
    }

    //Merge Sort can be applied to the product sorting problem because in all 3 cases (worst, average and best)
    // the complexity is O(nlogn). But Merge sort requires more memory than Quick sort.

    // Apply quick sort algorithm to sort products by price or quantity
    // Complexity, average and best case O(nlogn), worst case O(n^2).
    public List<Product> sortProductsBy(String sortBy, boolean ascending) {
        List<Product> sortedProducts = new ArrayList<>(products);
        quickSort(sortedProducts, 0, sortedProducts.size() - 1, sortBy, ascending);
        return sortedProducts;
    }

    //This function performs quick sorting by recursively dividing the list into smaller parts and sorting them.
    private void quickSort(List<Product> products, int low, int high, String sortBy, boolean ascending) {
        if (low < high) {
            int pi = partition(products, low, high, sortBy, ascending);// Get pivot index after sorting
            quickSort(products, low, pi - 1, sortBy, ascending);// Sort the left part of the pivot
            quickSort(products, pi + 1, high, sortBy, ascending);// Sort the right part of the pivot
        }
    }

    //This function selects an element as the pivot and rearranges the list so that all elements smaller than
    // the pivot are on the left and all elements larger than the pivot are on the right.
    private int partition(List<Product> products, int low, int high, String sortBy, boolean ascending) {
        Product pivot = products.get(high);// Select the last element as pivot
        int i = (low - 1);// Index of element is less than pivot
        for (int j = low; j < high; j++) {
            boolean condition;//condition: to store the result of the comparison condition.
            if (sortBy.equalsIgnoreCase("price")) {
                //Sets a comparison condition based on the value of ascending.
                condition = ascending ? products.get(j).getPrice() < pivot.getPrice() : products.get(j).getPrice() > pivot.getPrice();
            } else if (sortBy.equalsIgnoreCase("quantity")) {
                condition = ascending ? products.get(j).getQuantity() < pivot.getQuantity() : products.get(j).getQuantity() > pivot.getQuantity();
            } else {
                throw new IllegalArgumentException("Invalid sort field: " + sortBy);
            }
            //If the comparison condition is satisfied, swap the elements.
            if (condition) {
                i++;
                Product temp = products.get(i);
                products.set(i, products.get(j));
                products.set(j, temp);
            }
        }
        //Make sure the pivot is in the correct position in the sorted list.
        Product temp = products.get(i + 1);
        products.set(i + 1, products.get(high));
        products.set(high, temp);
        return i + 1;// Returns the pivot index after sorting
    }
}
