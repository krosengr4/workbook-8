import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner myScanner = new Scanner(System.in);

    static String password = System.getenv("SQL_PASSWORD");
    static String userName = "root";
    static String url = "jdbc:mysql://localhost:3306/northwind";

    static BasicDataSource dataSource = new BasicDataSource();
    static ProductDao productDao = new ProductDao(dataSource);
    static CustomerDao customerDao = new CustomerDao(dataSource);
    static CategoryDao categoryDao = new CategoryDao(dataSource);

    public static void main(String[] args) {

        setDataSource();
        boolean ifContinue = true;

        while (ifContinue) {
            String query = "";

            System.out.println("-----OPTIONS-----");
            System.out.println("1 - Display All Products\n2 - Display All Customers\n3 - Display All Categories\n4 - Display All Employees\n0 - Exit");
            System.out.println("Please select an option: ");
            int userQueryChoice = Integer.parseInt(myScanner.nextLine());

            switch (userQueryChoice) {
                case 1 -> processDisplayAllProducts();
                case 2 -> processDisplayAllCustomers();
                case 3 -> processDisplayAllCategories();
                case 4 -> query = "SELECT * from employees";
                case 0 -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter a number listed on the screen!");
            }

//            queryNorthwindColumn(userQueryChoice, query);

            System.out.println("\n\nWould you like to search for another? (Y or N): ");
            String userContinue = myScanner.nextLine();

            if (userContinue.equalsIgnoreCase("n")) {
                ifContinue = false;
            }
        }

        System.out.println("\n\nHave a Nice Day! :)");
    }

    public static void setDataSource() {
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
    }

    public static void processDisplayAllProducts() {
        ArrayList<Printable> productsList = productDao.getAllProducts();

        if (productsList.isEmpty()) {
            System.out.println("There are no products to display...");
        } else {
            printData(productsList);
        }
    }

    public static void processDisplayAllCustomers() {
        ArrayList<Printable> customersList = customerDao.getAllCustomers();

        if (customersList.isEmpty()) {
            System.out.println("There are no customers to display...");
        } else {
            printData(customersList);
        }
    }

    public static void processDisplayAllCategories() {
        ArrayList<Printable> categoriesList = categoryDao.getAllCategories();

        if (categoriesList.isEmpty()) {
            System.out.println("There are no categories to display...");
        } else {
            printData(categoriesList);

            System.out.println("\nWould you like to see products from a certain category? (Y or N)");
            System.out.println("Enter here: ");
            String userChoice = myScanner.nextLine().trim();

            if (userChoice.equalsIgnoreCase("y")) {
                displayProductsFromCategory();
            }
        }
    }

    public static void displayProductsFromCategory() {

        System.out.println("\nSelect a CategoryID Number (1-8): ");
        String userCatChoice = myScanner.nextLine();

        int userCatChoiceInt = Integer.parseInt(userCatChoice);

        boolean ifRetry = true;

        while (ifRetry) {
            if (userCatChoiceInt < 1 || userCatChoiceInt > 8) {
                System.err.println("ERROR! We only have 8 Categories! Enter a number between 1 and 8");
            } else {
                ArrayList<Printable> productsList = productDao.getProductsFromCatID(userCatChoice);

                if (productsList.isEmpty()) {
                    System.out.println("We have no products under that category...");
                } else {
                    printData(productsList);
                }

                ifRetry = false;
            }
        }

    }


    public static void queryNorthwindColumn(int userChoice, String query) {

        ArrayList<Printable> northwindData = new ArrayList<>();

        try (BasicDataSource dataSource = new BasicDataSource()) {

            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);

            Connection connection = dataSource.getConnection();

            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                if (userChoice == 1) {
                    int productID = Integer.parseInt(results.getString("ProductID"));
                    String productName = results.getString("ProductName");
                    double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                    int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));

                    Product newProduct = new Product(productID, productName, unitPrice, unitsInStock);
                    northwindData.add(newProduct);

                } else if (userChoice == 2) {
                    Customer newCustomer = new Customer(results.getString("ContactName"), results.getString("CompanyName"), results.getString("City"),
                            results.getString("Country"), results.getString("Phone"));
                    northwindData.add(newCustomer);

                } else if (userChoice == 3) {
                    int catID = Integer.parseInt(results.getString("CategoryID"));
                    String catName = results.getString("CategoryName");
                    String catDescription = results.getString("Description");

                    Category newCategory = new Category(catID, catName, catDescription);
                    northwindData.add(newCategory);

                } else if (userChoice == 4) {
                    Employee newEmployee = new Employee(results.getString("FirstName"), results.getString("LastName"), results.getString("Title"));
                    northwindData.add(newEmployee);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        printData(northwindData);
        if (userChoice == 3) {
            getCategoryProducts();
        }
    }

    public static void printData(ArrayList<Printable> northwindData) {
        if (northwindData.isEmpty()) {
            System.out.println("There is nothing in the list...");
        } else {
            for (Printable column : northwindData) {
                column.print();
                System.out.println("------------------------------");
            }
        }
    }

    public static void getCategoryProducts() {

        ArrayList<Printable> productData = new ArrayList<>();

        System.out.println("\n\nSelect a category ID to see its products: ");
        String userCatChoice = myScanner.nextLine().trim();

        try (BasicDataSource dataSource = new BasicDataSource()) {

            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);

            Connection connection = dataSource.getConnection();

            String secureQuery = "SELECT * FROM products WHERE CategoryID = ?";
            PreparedStatement prepStatement = connection.prepareStatement(secureQuery);
            prepStatement.setString(1, userCatChoice);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {

                int productID = Integer.parseInt(results.getString("ProductID"));
                String productName = results.getString("ProductName");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));

                Product newProduct = new Product(productID, productName, unitPrice, unitsInStock);
                productData.add(newProduct);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        printData(productData);
    }
}
