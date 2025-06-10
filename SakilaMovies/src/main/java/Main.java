import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static String url = "jdbc:mysql://localhost:3306/sakila";
    static String userName = "root";
    static String password = System.getenv("SQL_PASSWORD");

    public static void main(String[] args) {

        Scanner myScanner = new Scanner(System.in);

        System.out.println("Enter the last name of an actor: ");
        String actorLastName = myScanner.nextLine().trim();

        ArrayList<Printable> actorsList = findActors(actorLastName);

    }

    public static ArrayList<Printable> findActors(String actorLastName) {

        ArrayList<Printable> actorsList = new ArrayList<>();

        try (BasicDataSource dataSource = new BasicDataSource()) {
            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
            Connection connection = dataSource.getConnection();

            String secureQuery = "SELECT * FROM actor WHERE last_name = '?';";
            PreparedStatement preparedStatement = connection.prepareStatement(secureQuery);
            preparedStatement.setString(1, actorLastName);

            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                int actorID = Integer.parseInt(results.getString(1));
                String firstName = results.getString(2);
                String lastName = results.getString(3);
                Actor newActor = new Actor(actorID, firstName, lastName);

                actorsList.add(newActor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return actorsList;
    }
}
