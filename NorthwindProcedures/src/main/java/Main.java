import org.apache.commons.dbcp2.BasicDataSource;

import java.util.Scanner;

public class Main {

    static BasicDataSource dataSource = new BasicDataSource();
    static Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {

        setDataSource();
        boolean ifContinue = true;

        while (ifContinue) {
            System.out.println("-------OPTIONS-------");
            System.out.println("""
                    1 - Search for Customer Order History
                    2 - Search for Sales By Year
                    3 - Search for Sales By Category
                    0 - Exit
                    """);
            System.out.println("Enter your option: ");
            int userChoice = Integer.parseInt(myScanner.nextLine());

            switch (userChoice) {
                case 1 -> processCustomerOrderHist();
                case 2 -> processSalesByYear();
                case 3 -> processSalesByCategory();
                case 0 -> ifContinue = false;
                default -> System.err.println("ERROR! Please Enter A Number That Is Listed!");
            }
        }

        System.out.println("Thank you! Goodbye!");
    }

    public static void setDataSource() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));
    }

    public static void processCustomerOrderHist() {
        System.out.println("Customer Order History");
    }

    public static void processSalesByYear() {
        System.out.println("Sales by year");
    }

    public static void processSalesByCategory() {
        System.out.println("Sales by category");
    }

}
