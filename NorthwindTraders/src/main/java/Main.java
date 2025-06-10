import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {

        boolean ifContinue = true;

        while (ifContinue){
            String query = "";
            Scanner myScanner = new Scanner(System.in);

            System.out.println("-----OPTIONS-----");
            System.out.println("1 - See all product names\n2 - See product categories\n3 - See all employee names");
            System.out.println("Please select 1-3: ");
            int userQueryChoice = Integer.parseInt(myScanner.nextLine());

            switch (userQueryChoice) {
                case 1 -> query = "SELECT * from products;";
                case 2 -> query = "SELECT * from categories;";
                case 3 -> query = "SELECT * from employees;";
                default -> System.err.println("ERROR! Please enter 1 through 3!");
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


    public static void queryNorthwindColumn(int userChoice, String query) throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        String password = System.getenv("SQL_PASSWORD");
        String userName = "root";
        String url = "jdbc:mysql://localhost:3306/northwind";

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {

                if (userChoice == 1) {

                    int productID = Integer.parseInt(results.getString("ProductID"));
                    String productName = results.getString("ProductName");
                    double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                    int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));

                    Product newProduct = new Product(productID, productName, unitPrice, unitsInStock);
                    newProduct.printProduct();
                    System.out.println("-----------------------------");

                } else if (userChoice == 2) {
                    System.out.println("Category ID: " + results.getString("CategoryID"));
                    System.out.println("Category Name: " + results.getString("CategoryName"));
                    System.out.println("Description: " + results.getString("Description"));
                    System.out.println("----------------------------------------------");
                } else if (userChoice == 3) {
                    System.out.println("Employee Name: " + results.getString("FirstName") + " " + results.getString("LastName"));
                    System.out.println("Employee Title: " + results.getString("Title"));
                    System.out.println("-------------------------------------------");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
