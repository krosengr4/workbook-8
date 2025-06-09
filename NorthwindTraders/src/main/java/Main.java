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

        String userName = "root";
        String password = "611854kr";
        String url = "jdbc:mysql://localhost:3306/northwind";

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {

                if (userChoice == 1) {
                    String productName = results.getString("ProductName");

                    System.out.println("Product ID: " + results.getString("ProductID"));
                    System.out.println("Product Name: " + results.getString("ProductName"));
                    System.out.println("Product Price: $" + results.getString("UnitPrice"));
                    System.out.println("Stock: " + results.getString("UnitsInStock"));
                    System.out.println("----------------------------");

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
