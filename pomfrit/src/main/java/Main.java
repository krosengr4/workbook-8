import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Mapper is a dependency that we need to add in the pom.xml file
        ObjectMapper mapper;

        JDBCConnection();
    }

    public static void JDBCConnection() throws ClassNotFoundException, SQLException {
        // Load the mySQL driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        String username = "root";
        String password = System.getenv("SQL_PASSWORD");
        String url = "jdbc:mysql://localhost:3306/sakila";
        //define the query
        String query = "SELECT * from actor;";

        //Use the database URL to point to the correct database
        Connection connection = DriverManager.getConnection(url, username, password);

        // Create statement used tied to open the connection
        Statement statement = connection.createStatement();

        //execute the query
        ResultSet results = statement.executeQuery(query);

        // process the results
        while (results.next()) {
            String firstName = results.getString("first_name");
            System.out.println(firstName);
        }

        //Close the connection
        connection.close();

    }
}
