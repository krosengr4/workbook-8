import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDao {

    private final DataSource dataSource;

    public CategoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<Printable> getAllCategories() {

        ArrayList<Printable> categoriesList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * from categories ORDER BY CategoryID;";
            PreparedStatement prepStatement = conn.prepareStatement(query);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                int catID = Integer.parseInt(results.getString("CategoryID"));
                String catName = results.getString("CategoryName");
                String catDescription = results.getString("Description");

                Category newCategory = new Category(catID, catName, catDescription);
                categoriesList.add(newCategory);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoriesList;
    }


}
