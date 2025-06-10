import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner myScanner = new Scanner(System.in);

    static String password = System.getenv("SQL_PASSWORD");
    static String userName = "root";
    static String url = "jdbc:mysql://localhost:3306/northwind";

    public static void main(String[] args) {

        boolean ifContinue = true;

        while (ifContinue) {
            String query = "";

            System.out.println("-----OPTIONS-----");
            System.out.println("1 - See all product names\n2 - See product categories\n3 - See all employee names\n4 - See all customers\n0 - Exit");
            System.out.println("Please select an option: ");
            int userQueryChoice = Integer.parseInt(myScanner.nextLine());

            switch (userQueryChoice) {
                case 1 -> query = "SELECT * from products;";
                case 2 -> query = "SELECT * from categories ORDER BY CategoryID;";
                case 3 -> query = "SELECT * from employees;";
                case 4 -> query = "SELECT * from customers ORDER BY Country;";
                case 0 -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter a number listed on the screen!");
            }

            queryNorthwindColumn(userQueryChoice, query);

            System.out.println("\n\nWould you like to search for another? (Y or N): ");
            String userContinue = myScanner.nextLine();

            if (userContinue.equalsIgnoreCase("n")) {
                ifContinue = false;
            }
        }

        System.out.println("\n\nHave a Nice Day! :)");
    }


    public static void queryNorthwindColumn(int userChoice, String query) {

        ArrayList<Printable> northwindData = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

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
                    int catID = Integer.parseInt(results.getString("CategoryID"));
                    String catName = results.getString("CategoryName");
                    String catDescription = results.getString("Description");

                    Category newCategory = new Category(catID, catName, catDescription);
                    northwindData.add(newCategory);

                } else if (userChoice == 3) {
                    Employee newEmployee = new Employee(results.getString("FirstName"), results.getString("LastName"), results.getString("Title"));
                    northwindData.add(newEmployee);

                } else if (userChoice == 4) {
                    Customer newCustomer = new Customer(results.getString("ContactName"), results.getString("CompanyName"), results.getString("City"),
                            results.getString("Country"), results.getString("Phone"));
                    northwindData.add(newCustomer);

                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        printData(northwindData);
        if (userChoice == 2) {
            getCategoryProducts();
        }
    }

    public static void printData(ArrayList<Printable> northwindData) {
        for (Printable column : northwindData) {
            column.print();
            System.out.println("------------------------------");
        }
    }

    public static void getCategoryProducts() {

        ArrayList<Printable> productData = new ArrayList<>();

        System.out.println("Select a category ID to see its products: ");
        String userCatChoice = myScanner.nextLine().trim();

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {

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
