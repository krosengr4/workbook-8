public class Product {

    int productID;
    String name;
    double unitPrice;
    int unitsInStock;

    public Product (int productID, String name, double unitPrice, int unitsInStock) {
        this.productID = productID;
        this.name = name;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
    }

    public void printProduct() {
        System.out.println("Product ID: " + this.productID);
        System.out.println("Product Name: " + this.name);
        System.out.printf("Unit Price: $%.2f\n", this.unitPrice);
        System.out.println("Units In Stock: " + this.unitsInStock);
    }

}
