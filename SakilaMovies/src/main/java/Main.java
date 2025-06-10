import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static String url = "jdbc:mysql://localhost:3306/sakila";
    static String userName = "root";
    static String password = System.getenv("SQL_PASSWORD");
    static Scanner myScanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean ifContinue = true;

        while (ifContinue) {
            searchForActors();
            System.out.println("Would you like to search again? (Y or N): ");
            String userTryAgain = myScanner.nextLine().trim();

            if (userTryAgain.equalsIgnoreCase("n")){
                ifContinue = false;
                System.out.println("Have A Nice Day! :)");
            }
        }
    }

    public static void searchForActors() {

        boolean continueActorSearch = true;

        while (continueActorSearch) {
            System.out.println("Enter the last name of an actor: ");
            String actorLastName = myScanner.nextLine().trim();

            ArrayList<Printable> actorsList = findActors(actorLastName);

            if (actorsList.isEmpty()) {
                System.out.println("There were no actors with that last name...");
            } else {
                printData(actorsList);
                continueActorSearch = false;
                searchForFilm();
            }
        }
    }

    public static void searchForFilm() {

        boolean ifContinue = true;

        while (ifContinue) {
            System.out.println("\nPlease enter the actors full name ('firstName lastName') to see what films they are in!");
            System.out.println("Enter here: ");
            String actorFullName = myScanner.nextLine().trim();

            ArrayList<Printable> filmsList = findFilms(actorFullName);

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

    public static ArrayList<Printable> findFilms(String actorFullName) {

        ArrayList<Printable> filmsList = new ArrayList<>();
        String[] nameParts = actorFullName.split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts[1];

        try (BasicDataSource dataSource = new BasicDataSource()) {
            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);

            ResultSet results = getFilmResults(firstName, lastName, dataSource);

            while (results.next()) {
                String title = results.getString("title");
                String description = results.getString("description");
                String releaseYear = results.getString("release_year");
                int length = Integer.parseInt(results.getString("length"));
                String rating = results.getString("rating");

                Film newFilm = new Film(title, description, releaseYear, length, rating);
                filmsList.add(newFilm);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return filmsList;
    }

    private static ResultSet getFilmResults(String firstName, String lastName, BasicDataSource dataSource) throws SQLException{
        Connection connection = dataSource.getConnection();

        String secureQuery = "SELECT * FROM film " +
                "JOIN film_actor ON film_actor.film_id = film.film_id " +
                "WHERE actor_id = (SELECT actor_id FROM actor " +
                "WHERE first_name = ? AND last_name = ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(secureQuery);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);

        return preparedStatement.executeQuery();
    }

    public static ArrayList<Printable> findActors(String actorLastName) {

        ArrayList<Printable> actorsList = new ArrayList<>();

        try (BasicDataSource dataSource = new BasicDataSource()) {
            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
            Connection connection = dataSource.getConnection();

            String secureQuery = "SELECT * FROM actor WHERE last_name = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(secureQuery);
            preparedStatement.setString(1, actorLastName);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int actorID = Integer.parseInt(results.getString("actor_id"));
                String firstName = results.getString("first_name");
                String lastName = results.getString("last_name");
                Actor newActor = new Actor(actorID, firstName, lastName);

                actorsList.add(newActor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return actorsList;
    }

    public static void printData(ArrayList<Printable> dataList) {
        for (Printable column : dataList) {
            column.print();
            System.out.println("--------------------------------");
        }

    }
}
