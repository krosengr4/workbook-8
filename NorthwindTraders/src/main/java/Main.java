import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    static BasicDataSource dataSource = new BasicDataSource();

    static ProductDao productDao = new ProductDao(dataSource);
    static CustomerDao customerDao = new CustomerDao(dataSource);
    static CategoryDao categoryDao = new CategoryDao(dataSource);
    static EmployeeDao employeeDao = new EmployeeDao(dataSource);

    public static void main(String[] args) {

        setDataSource();
        boolean ifContinue = true;

        while (ifContinue) {

            System.out.println("-----OPTIONS-----");
            System.out.println("1 - Display All Products\n2 - Display All Customers\n3 - Display All Categories\n4 - Display All Employees\n0 - Exit");
            int userQueryChoice = Utils.messageAndResponseInt("Please select an option: ");

            switch (userQueryChoice) {
                case 1 -> processAllProducts();
                case 2 -> processAllCustomers();
                case 3 -> processAllCategories();
                case 4 -> processAllEmployees();
                case 0 -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter a number listed on the screen!");
            }
        }

        System.out.println("\n\nHave a Nice Day! :)");
    }

    public static void setDataSource() {
        String password = System.getenv("SQL_PASSWORD");
        String userName = "root";
        String url = "jdbc:mysql://localhost:3306/northwind";

        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
    }

    public static void processAllProducts() {
        ArrayList<Printable> productsList = productDao.getAllProducts();

        if (productsList.isEmpty()) {
            System.out.println("There are no products to display...");
        } else {
            printData(productsList);
        }
    }

    public static void processAllCustomers() {
        ArrayList<Printable> customersList = customerDao.getAllCustomers();

        if (customersList.isEmpty()) {
            System.out.println("There are no customers to display...");
        } else {
            printData(customersList);
        }
    }

    public static void processAllCategories() {
        ArrayList<Printable> categoriesList = categoryDao.getAllCategories();

        if (categoriesList.isEmpty()) {
            System.out.println("There are no categories to display...");
        } else {
            printData(categoriesList);

            System.out.println("\nWould you like to see products from a certain category? (Y or N)");
            String userChoice = Utils.promptGetUserInput("Enter here: ");

            if (userChoice.equalsIgnoreCase("y")) {
                displayProductsFromCategory();
            }
        }
    }

    public static void displayProductsFromCategory() {

        String userCatChoice = Utils.promptGetUserInput("\nSelect a CategoryID Number (1-8): ");
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

    public static void processAllEmployees() {
        ArrayList<Printable> employeesList = employeeDao.getAllEmployees();

        if (employeesList.isEmpty()) {
            System.out.println("There are no employees to display...");
        } else {
            printData(employeesList);
        }
    }

    public static void printData(ArrayList<Printable> northwindData) {

        for (Printable column : northwindData) {
            column.print();
            System.out.println("------------------------------");
        }

        Utils.pauseApp();
    }
}
