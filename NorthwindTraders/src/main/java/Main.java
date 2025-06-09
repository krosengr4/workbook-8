import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        String userName = "root";
        String password = "611854kr";
        String url = "jdbc:mysql://localhost:3306/northwind";
        String query = "SELECT * from products;";

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            while (results.next()) {
                String productName = results.getString("ProductName");
                System.out.println(productName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
