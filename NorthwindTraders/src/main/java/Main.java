import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        boolean ifContinue = true;

        while (ifContinue){
            String query = "";
            Scanner myScanner = new Scanner(System.in);

            System.out.println("-----OPTIONS-----");
            System.out.println("1 - See all product names\n2 - See product categories\n3 - See all employee names\n4 - See all customers\n0 - Exit");
            System.out.println("Please select 1-3: ");
            int userQueryChoice = Integer.parseInt(myScanner.nextLine());

            switch (userQueryChoice) {
                case 1 -> query = "SELECT * from products;";
                case 2 -> query = "SELECT * from categories;";
                case 3 -> query = "SELECT * from employees;";
                case 4 -> query = "SELECT * from customers;";
                case 0 -> ifContinue = false;
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


    public static void queryNorthwindColumn(int userChoice, String query) {

        ArrayList<Printable> northwindData = new ArrayList<>();

        String password = System.getenv("SQL_PASSWORD");
        String userName = "root";
        String url = "jdbc:mysql://localhost:3306/northwind";

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
    }

    public static void printData(ArrayList<Printable> northwindData) {
        for (Printable column : northwindData) {
            column.print();
            System.out.println("------------------------------");
        }
    }
}
