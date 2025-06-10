public class Category {

    int categoryID;
    String name;
    String description;

    public Category (int categoryID, String name, String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
    }

    public void printCategory() {
        System.out.println("Category ID: " + this.categoryID);
        System.out.println("Category Name: " + this.name);
        System.out.println("Category Description: " + this.description);
    }

}
