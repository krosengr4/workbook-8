import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private final DataSource dataSource;

    public ProductDao (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<Printable> getAllProducts() {

        ArrayList<Printable> productsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM products;";
            PreparedStatement prepStatement = conn.prepareStatement(query);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                int productID = Integer.parseInt(results.getString("ProductID"));
                String productName = results.getString("ProductName");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));

                Product newProduct = new Product(productID, productName, unitPrice, unitsInStock);
                productsList.add(newProduct);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productsList;
    }

    public ArrayList<Printable> getProductsFromCatID(String userCatChoice) {

        ArrayList<Printable> productsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM products WHERE CategoryID = ?";
            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userCatChoice);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                int productID = Integer.parseInt(results.getString("ProductID"));
                String productName = results.getString("ProductName");
                double unitPrice = Double.parseDouble(results.getString("UnitPrice"));
                int unitsInStock = Integer.parseInt(results.getString("UnitsInStock"));

                Product newProduct = new Product(productID, productName, unitPrice, unitsInStock);

                productsList.add(newProduct);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productsList;
    }

}
