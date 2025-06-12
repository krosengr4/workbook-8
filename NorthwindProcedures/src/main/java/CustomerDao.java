import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDao {

    private final DataSource dataSource;

    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<NorthwindData> getCustomerOrderHistory(String userInput) {
        ArrayList<NorthwindData> customerHistoryList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "CALL CustOrderHist(?);";
            CallableStatement statement = conn.prepareCall(query);
            statement.setString(1, userInput);

            ResultSet results = statement.executeQuery();

            while (results.next()) {
                String productName = results.getString("ProductName");
                double total = Double.parseDouble(results.getString("TOTAL"));

                Order order = new Order(productName, total);
                customerHistoryList.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customerHistoryList;
    }
}
