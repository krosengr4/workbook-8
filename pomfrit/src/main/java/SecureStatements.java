import java.sql.*;
import java.util.Scanner;

public class SecureStatements {

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("Select a film title to search: ");
        String userInput = myScanner.nextLine();

        try {
            //Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String username = "root";
        String passWord = System.getenv("SQL_PASSWORD");
        String url = "jdbc:mysql://localhost:3306/sakila";

        //We use the try/catch with resources so we don't have to implicitly close the db connection (safer)
        try (Connection connection = DriverManager.getConnection(url, username, passWord)) {
            // This secureQuery will replace the "?" with the user input
            // This is so that the user cannot run a "DROP TABLE;" command
            String secureQuery = "SELECT * FROM film WHERE title = ?";

            // This is how we create the sql statement / open the db, but with the secure query
            PreparedStatement preparedStatement = connection.prepareStatement(secureQuery);
//          Statement statement = connection.createStatement();

            // in setString() the 1 goes with the question mark and replaces with the userInput.
            preparedStatement.setString(1, userInput);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                System.out.println("Title: " + results.getString("title"));
                System.out.println("Description: " + results.getString("description"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
