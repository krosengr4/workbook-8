import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SakilaDataManager {

    private final DataSource dataSource;

    public SakilaDataManager (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<Printable> getActors(String actorLastName) {
        ArrayList<Printable> actorsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT * from actor WHERE last_name = ?;";
            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, actorLastName);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                int actorID = Integer.parseInt(results.getString("actor_id"));
                String firstName = results.getString("first_name");
                String lastName = results.getString("last_name");

                Actor newActor = new Actor(actorID, firstName, lastName);
                actorsList.add(newActor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return actorsList;
    }
}
