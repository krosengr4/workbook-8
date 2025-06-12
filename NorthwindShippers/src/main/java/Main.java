import org.apache.commons.dbcp2.BasicDataSource;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static BasicDataSource dataSource = new BasicDataSource();
    static ShipperDao shipperDao = new ShipperDao(dataSource);
    static Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {

        setDataSource();
        boolean ifContinue = true;

        while (ifContinue) {
            System.out.println("\n\n____________SHIPPER OPTIONS____________");
            System.out.println("""
                    1 - Display All Shippers
                    2 - Add New Shipper
                    3 - Update A Shipper
                    4 - Delete A Shipper
                    0 - Exit""");

            System.out.println("Enter your option: ");
            int userShipperChoice = Integer.parseInt(myScanner.nextLine());

            switch (userShipperChoice) {
                case 1 -> displayAllShippers();
                case 2 -> insertNewShipper();
                case 3 -> updateShipper();
                case 4 -> deleteShipper();
                case 0 -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter a number that is listed!");
            }
        }
        System.out.println("Thank you! Goodbye!");
    }

    public static void setDataSource() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword(System.getenv("SQL_PASSWORD"));
    }

    public static void displayAllShippers() {
        ArrayList<NorthwindData> shippersList = shipperDao.getAllShippers();
        printData(shippersList);
    }

    public static void insertNewShipper() {
        System.out.println("Enter the company name: ");
        String companyName = myScanner.nextLine();
        System.out.println("Enter the company phone number: ");
        String phoneNumber = myScanner.nextLine();

        shipperDao.insertShipperIntoDB(companyName, phoneNumber);
    }

    public static void updateShipper() {
        System.out.println("-----OPTIONS-----\n1 - Update Company Name\n2 - Update Phone Number");
        int userUpdateOption = Integer.parseInt(myScanner.nextLine());

        System.out.println("Insert the ID of the Shipper to update");
        int shipperID = Integer.parseInt(myScanner.nextLine());

        String newValue = "";
        if (userUpdateOption == 1) {
            System.out.println("Please enter the new company name: ");
            newValue = myScanner.nextLine().trim();
        } else if (userUpdateOption == 2) {
            System.out.println("Please enter the new phone number: ");
            newValue = myScanner.nextLine().trim();
        }

        shipperDao.updateShipper(userUpdateOption, shipperID, newValue);
    }

    public static void deleteShipper() {
        boolean ifContinue = true;

        while (ifContinue) {

            System.out.println("Enter the ID of the Shipper you want to delete (cannot be 1, 2 or 3!). Enter 0 to go back.");
            System.out.println("Enter Here: ");
            int shipperID = Integer.parseInt(myScanner.nextLine());

            if (shipperID == 1 || shipperID == 2 || shipperID == 3) {
                System.err.println("Nice try bucko! Do not enter ShipperID 1, 2 or 3!!!");
            } else if (shipperID == 0) {
                ifContinue = false;
            } else {
                shipperDao.deleteShipper(shipperID);
                ifContinue = false;
            }
        }
    }

    public static void printData(ArrayList<NorthwindData> dataList) {

        if (dataList.isEmpty()) {
            System.out.println("There is no data to display...");
        } else {
            for (NorthwindData column : dataList) {
                System.out.println("---------------------------------");
                column.print();
                System.out.println("---------------------------------");
            }
        }

    }

}
