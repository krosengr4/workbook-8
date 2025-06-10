public class Category implements Printable{

    int categoryID;
    String name;
    String description;

    public Category (int categoryID, String name, String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
    }

    public void print() {
        System.out.println("Category ID: " + this.categoryID);
        System.out.println("Category Name: " + this.name);
        System.out.println("Category Description: " + this.description);
    }

}
