import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Mapper is a dependency that we need to add in the pom.xml file
        ObjectMapper mapper;

        JDBCConnection();
    }

    public static void JDBCConnection() throws ClassNotFoundException, SQLException {
        // Load the mySQL driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        Scanner myScanner = new Scanner(System.in);

        String username = "root";
        String password = System.getenv("SQL_PASSWORD");
        String url = "jdbc:mysql://localhost:3306/sakila";
        //define the query
//        String query = "SELECT * from actor;";

        System.out.println("Select a film you want to see: ");
        String userFilm = myScanner.nextLine();

        String query = "SELECT * from film WHERE title = '" + userFilm + "';";

        //Use the database URL to point to the correct database
        Connection connection = DriverManager.getConnection(url, username, password);

        // Create statement used tied to open the connection
        Statement statement = connection.createStatement();

        //execute the query
        ResultSet results = statement.executeQuery(query);

        // process the results
//        while (results.next()) {
//            String firstName = results.getString("first_name");
//            System.out.println(firstName);
//        }

        while (results.next()) {
            System.out.println("Film Title: " + results.getString("title"));
            System.out.println("Film Description: " + results.getString("description"));
        }

        //Close the connection
        connection.close();

    }
}
