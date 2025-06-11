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

    public ArrayList<Printable> getFilms(String actorFirstName, String actorLastName) {
        ArrayList<Printable> filmsList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String query = "SELECT t1.*, t2.actor_id, t2.first_name, t2.last_name from film t1, actor t2, film_actor t3 " +
                    "WHERE t1.film_id = t3.film_id " +
                    "AND t3.actor_id = t2.actor_id " +
                    "AND first_name = ? AND last_name = ?;";
            PreparedStatement prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, actorFirstName);
            prepStatement.setString(2, actorLastName);

            ResultSet results = prepStatement.executeQuery();

            while (results.next()) {
                String title = results.getString("title");
                String description = results.getString("description");
                String releaseYear = results.getString("release_year");
                int length = Integer.parseInt(results.getString("length"));
                String rating = results.getString("rating");

                Film newFilm = new Film(title, description, releaseYear, length, rating);

                filmsList.add(newFilm);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return filmsList;
    }
}
