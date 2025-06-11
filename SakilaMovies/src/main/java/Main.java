import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static String url = "jdbc:mysql://localhost:3306/sakila";
    static String userName = "root";
    static String password = System.getenv("SQL_PASSWORD");
    static Scanner myScanner = new Scanner(System.in);

    static BasicDataSource dataSource = new BasicDataSource();
    static SakilaDataManager dataManager = new SakilaDataManager(dataSource);

    public static void main(String[] args) {

        setDataSource();
        boolean ifContinue = true;

        while (ifContinue) {

            searchForActors();
            searchForFilm();

            System.out.println("Would you like to search again? (Y or N): ");
            String userTryAgain = myScanner.nextLine().trim();

            if (userTryAgain.equalsIgnoreCase("n")){
                ifContinue = false;
                System.out.println("Have A Nice Day! :)");
            }
        }
    }

    public static void setDataSource() {
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
    }

    public static void searchForActors() {

        boolean continueActorSearch = true;

        while (continueActorSearch) {
            System.out.println("Enter the last name of an actor: ");
            String actorLastName = myScanner.nextLine().trim();

            ArrayList<Printable> actorsList = dataManager.getActors(actorLastName);

            if (actorsList.isEmpty()) {
                System.out.println("There were no actors with that last name...");
            } else {
                printData(actorsList);
                continueActorSearch = false;

            }
        }
    }

    public static void searchForFilm() {

        boolean ifContinue = true;

        while (ifContinue) {
            System.out.println("\nPlease enter the actors full name ('firstName lastName') to see what films they are in!");
            System.out.println("Enter here: ");
            String actorFullName = myScanner.nextLine().trim();

            String[] actorNameParts = actorFullName.split(" ");
            String actorFirstName = actorNameParts[0];
            String actorLastName = actorNameParts[1];

            ArrayList<Printable> filmsList = dataManager.getFilms(actorFirstName, actorLastName);

            if (filmsList.isEmpty()) {
                System.out.println("There are no films with that actor...");
                System.out.println("Try again? (Y or N): ");
                String userTryAgain = myScanner.nextLine().trim();

                if (userTryAgain.equalsIgnoreCase("n")) {
                    ifContinue = false;
                }
            } else {
                System.out.printf("\t\t-------FILMS WITH %s-------\n", actorFullName.toUpperCase());
                printData(filmsList);
                ifContinue = false;
            }
        }
    }

    public static void printData(ArrayList<Printable> dataList) {
        for (Printable column : dataList) {
            column.print();
            System.out.println("--------------------------------");
        }

    }
}
