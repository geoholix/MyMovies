package amri.k.mymovies.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import amri.k.mymovies.Models.MovieList;
import amri.k.mymovies.Models.MovieResponse;
import amri.k.mymovies.adapter.MovieListAdapter;

/**
 * Created by amri on 2/3/2018.
 */

public class MovieJsonUtils {

    private static MovieResponse movieResponse = new MovieResponse();
    private static List<MovieList> movie_list;
    private static MovieListAdapter movieListAdapter;

    public static void getSimpleMovieStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {
        JSONObject movieJson = new JSONObject(movieJsonStr);

        movieResponse.setPage(movieJson.getDouble("page"));

        JSONArray results = movieJson.getJSONArray("results");

        movieResponse.setTotalPages(movieJson.getDouble("total_pages"));

        movieResponse.setTotalResults(movieJson.getDouble("total_results"));

        for (int i=0; i<results.length(); i++){
            JSONObject obj = results.getJSONObject(i);

            MovieList addMovie = new MovieList();
            addMovie.posterPath = obj.getString("poster_path");
            addMovie.voteCount = obj.getLong("vote_count");
            addMovie.overview = obj.getString("overview");
            addMovie.voteAverage = obj.getDouble("vote_average");
            addMovie.releaseDate = obj.getString("release_date");
            addMovie.id = obj.getInt("id");

            movie_list.add(addMovie);
        }
    }
}
