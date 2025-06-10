import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/sakila";
        String userName = "root";
        String password = System.getenv("SQL_PASSWORD");

        //! Here we are using DataSource connection instead of Driver Manager
        try (BasicDataSource dataSource = new BasicDataSource()) {

            //Setting the url, username, and password
            dataSource.setUrl(url);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);

            Connection connection = dataSource.getConnection();

            String query = "SELECT * FROM actor WHERE first_name = 'julia';";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                //We can get the column results with an index (1 indexed)
                System.out.println("First Name: " + results.getString(2));
                System.out.println("Last Name: " + results.getString(3));
                System.out.println("--------------------------");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
