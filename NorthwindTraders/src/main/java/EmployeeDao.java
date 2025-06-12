import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeDao {

    private final DataSource dataSource;

    public EmployeeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<Printable> getAllEmployees() {
        ArrayList<Printable> employeesList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * FROM employees;";
            PreparedStatement prepStatement = conn.prepareStatement(query);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                String firstName = results.getString("FirstName");
                String lastName = results.getString("LastName");
                String title = results.getString("Title");

                Employee newEmployee = new Employee(firstName, lastName, title);

                employeesList.add(newEmployee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeesList;
    }

}
