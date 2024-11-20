package dk.easv.mrs.DAL.DB;
// project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.DAL.IMovieDataAccess;
import dk.easv.mrs.DAL.DB.DBConnector;
// java imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_Db implements IMovieDataAccess {

    @Override
    public List<Movie> getAllMovies() throws Exception {
        DBConnector db = new DBConnector();
        String sql = "SELECT * FROM Movie";
        List<Movie> movies = new ArrayList<Movie>();
        try(Connection conn = db.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Movie m = new Movie(rs.getInt("Id"), rs.getInt("Year"), rs.getString("Title"));
                movies.add(m);
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }

        return movies;
    }

    @Override
    public Movie createMovie(Movie newMovie) throws Exception {
        DBConnector db = new DBConnector();
        String sql = "INSERT INTO Movie (Year, Title) VALUES (?, ?)";
        if (!checkIfMovieExists(newMovie)) {
            try (Connection conn = db.getConnection(); PreparedStatement preStmt = conn.prepareStatement(sql)) {
                preStmt.setInt(1, newMovie.getYear());
                preStmt.setString(2, newMovie.getTitle());
                preStmt.executeUpdate();
                preStmt.close();
                sql = "SELECT Id FROM Movie WHERE Year = ? AND Title = ?";
                PreparedStatement search = conn.prepareStatement(sql);
                search.setInt(1, newMovie.getYear());
                search.setString(2, newMovie.getTitle());
                ResultSet rs = search.executeQuery();
                rs.next(); // moves to first position
                return new Movie(rs.getInt("Id"), newMovie.getYear(), newMovie.getTitle());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else{
            throw new RuntimeException("Movie already exists");
        }
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
        DBConnector db = new DBConnector();
        String sql = "UPDATE Movie SET Year = ?, Title = ? WHERE Id = ?";
        try (Connection conn = db.getConnection(); PreparedStatement preStmt = conn.prepareStatement(sql)) {
            preStmt.setInt(1, movie.getYear());
            preStmt.setString(2, movie.getTitle());
            preStmt.setInt(3, movie.getId());
            preStmt.executeUpdate();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }

    private boolean checkIfMovieExists(Movie movie) throws Exception {
        DBConnector db = new DBConnector();
        String sql = "SELECT * FROM Movie WHERE Year = ? AND Title = ?";
        try(Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movie.getYear());
            stmt.setString(2, movie.getTitle());
            return stmt.executeQuery().next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
