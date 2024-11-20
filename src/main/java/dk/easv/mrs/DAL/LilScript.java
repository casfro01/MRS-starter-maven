package dk.easv.mrs.DAL;

// project imports
import dk.easv.mrs.DAL.DB.DBConnector;

// java imports
import java.io.IOException;
import java.sql.Connection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LilScript {

    // transfer data
    public static void main(String[] args) throws Exception {
        removeDuplicates();
    }

    static void addAll() throws Exception {

        // file access
        Path filePath = Path.of("data/movie_titles.txt");
        String sql = "INSERT INTO Movie (Title, Year) VALUES (?, ?)";
        DBConnector db = new DBConnector();
        try (Connection conn = db.getConnection()) {
            List<String> lines = Files.readAllLines(filePath);

            // database access
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (String line : lines) {
                String[] token = line.split(",");
                ps.setString(1, token[2]);
                ps.setInt(2, Integer.parseInt(token[1]));
                ps.executeUpdate();
            }
        }
        catch (Exception e){
            throw new IOException(e.getMessage());
        }
    }

    static void removeDuplicates() throws Exception {
        String sql = "SELECT Title FROM Movie";
        DBConnector db = new DBConnector();
        Set<String> titles = new HashSet<>();
        try (Connection conn = db.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            // får unikke titler
            while (rs.next()) {
                titles.add(rs.getString("Title"));
            }

            String searchMoviesSQL = "SELECT * FROM Movie WHERE Title LIKE ?";
            String deleteMultiple = "DELETE FROM Movie WHERE Id = ?";
            PreparedStatement psSer = conn.prepareStatement(searchMoviesSQL);
            PreparedStatement psDel = conn.prepareStatement(deleteMultiple);
            for (String title : titles) {
                psSer.setString(1, title);
                ResultSet res = psSer.executeQuery();
                int counter = 0;
                while (res.next()) {
                    if (counter > 0){
                        psDel.setInt(1, res.getInt("Id"));
                        psDel.executeUpdate();
                    }
                    counter++;
                }
            }
        }
    }
}
