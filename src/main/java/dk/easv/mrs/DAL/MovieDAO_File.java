package dk.easv.mrs.DAL;
// project imports
import dk.easv.mrs.BE.Movie;
// java imports
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";

    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
    @Override
    public List<Movie> getAllMovies() throws IOException {
        try{
            List<Movie> movies = new ArrayList<Movie>();
            File f = new File(MOVIES_FILE);
            Scanner sc = new Scanner(f); // scans the file for information
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] tokens = line.split(","); // the data from each movie based on the files structure.
                movies.add(new Movie( Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), tokens[2])); // adds to the list
            }
            sc.close(); // remember to close it
            return movies;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a move and returns it if it's successful
     * @param title The title of the movie
     * @param year The year of its release - as an int
     * @return returns the movie if it's successful, if it's not it returns null
     * @throws Exception Can throw exception if there is file problems
     */
    @Override
    public Movie createMovie(String title, int year) throws Exception {
        try{
            File movieFile = new File(MOVIES_FILE);
            Scanner in = new Scanner(movieFile);
            String lastLine = "";
            while(in.hasNextLine()){
                lastLine = in.nextLine();
            }
            in.close(); // close the scanner - important
            int id = Integer.parseInt(lastLine.split(",")[0]) + 1; // creates a new id based on the last element
            FileWriter fw = new FileWriter(MOVIES_FILE, true); // we want to append to the file and not overwrite
            Movie m = new Movie(id, year, title); // new movie which needs to be appended
            fw.append(m.toData()).append("\n"); // adds the string to the file
            fw.close(); // remember to close it
            return m;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // update the given movie
    // find it and update it
    @Override
    public void updateMovie(Movie movie) throws Exception {
    }

    // delete the movie
    @Override
    public void deleteMovie(Movie movie) throws Exception {
    }
}