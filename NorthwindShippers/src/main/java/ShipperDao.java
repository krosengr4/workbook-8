import javax.sql.DataSource;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class ShipperDao {

    private final DataSource dataSource;

    public ShipperDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<NorthwindData> getAllShippers() {

        ArrayList<NorthwindData> shippersList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM shippers;";
            PreparedStatement prepStatement = conn.prepareStatement(query);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                String companyName = results.getString("CompanyName");
                String phoneNumber = results.getString("Phone");

                shippersList.add(new Shipper(companyName, phoneNumber));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return shippersList;
    }

    public void insertShipperIntoDB (String companyName, String phoneNumber) {
        try (Connection conn = dataSource.getConnection()) {

            String query = "INSERT INTO shippers (CompanyName, Phone) " +
                    "VALUES (?, ?);";
            PreparedStatement prepStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            prepStatement.setString(1, companyName);
            prepStatement.setString(2, phoneNumber);

            int rows = prepStatement.executeUpdate();
            System.out.println("Rows Updated: " + rows);

            try (ResultSet keys = prepStatement.getGeneratedKeys()) {
                while (keys.next()) {
                    System.out.println("Key Added: " + keys.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
