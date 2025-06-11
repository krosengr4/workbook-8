import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner myScanner = new Scanner(System.in);

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
            System.out.println("Please select an option: ");
            int userQueryChoice = Integer.parseInt(myScanner.nextLine());

            switch (userQueryChoice) {
                case 1 -> processDisplayAllProducts();
                case 2 -> processDisplayAllCustomers();
                case 3 -> processDisplayAllCategories();
                case 4 -> processAllEmployees();
                case 0 -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter a number listed on the screen!");
            }

            System.out.println("\n\nWould you like to search for another? (Y or N): ");
            String userContinue = myScanner.nextLine();

            if (userContinue.equalsIgnoreCase("n")) {
                ifContinue = false;
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

    public static void processAllEmployees() {
        ArrayList<Printable> employeesList = employeeDao.getAllEmployees();

        if (employeesList.isEmpty()) {
            System.out.println("There are no employees to display...");
        } else {
            printData(employeesList);
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
}
