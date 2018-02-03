package amri.k.mymovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import amri.k.mymovies.DetailActivity;
import amri.k.mymovies.Models.MovieList;
import amri.k.mymovies.R;
import amri.k.mymovies.utilities.NetworkUtils;

/**
 * Created by amri on 2/3/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    Context context;
    private List<MovieList> movie_list;
    String MOVIE_TITLE = "title";
    String MOVIE_OVERVIEW = "overview";
    String MOVIE_RELEASE = "release_date";
    String MOVIE_POSTER = "poster_path";
    String MOVIE_VOTE_AVERAGE = "vote_average";
    String MOVIE_VOTE_COUNT = "vote_count";
    String MOVIE_BACK_DROP = "backdrop_path";


    public MovieListAdapter(List<MovieList> movies){
        this.movie_list = movies;
    }

    public void setMovieData(List<MovieList> movieData){
        movie_list = movieData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView, ratingTextView, voterTextView, releaseTextView;
        ImageView posterImageView;
        RatingBar ratingBar;

        public ViewHolder(View view){
            super(view);
            view.setClickable(true);
            view.setOnClickListener(this);

            titleTextView = view.findViewById(R.id.tv_movie_title);
            ratingTextView = view.findViewById(R.id.rating_score);
            voterTextView = view.findViewById(R.id.num_of_votes);
            releaseTextView = view.findViewById(R.id.year);
            ratingBar = view.findViewById(R.id.rating_bar);
            posterImageView = view.findViewById(R.id.iv_poster);
            ratingBar.setStepSize((float)0.1);
            context = view.getContext();
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Intent movieDetail = new Intent(context, DetailActivity.class);
            movieDetail.putExtra(MOVIE_TITLE, movie_list.get(position).title);
            movieDetail.putExtra(MOVIE_OVERVIEW, movie_list.get(position).overview);
            movieDetail.putExtra(MOVIE_POSTER, movie_list.get(position).posterPath);
            movieDetail.putExtra(MOVIE_BACK_DROP, movie_list.get(position).backdropPath);
            movieDetail.putExtra(MOVIE_RELEASE, movie_list.get(position).releaseDate);
            movieDetail.putExtra(MOVIE_VOTE_AVERAGE, String.valueOf(movie_list.get(position).voteAverage));
            movieDetail.putExtra(MOVIE_VOTE_COUNT, String.valueOf(movie_list.get(position).voteCount));
            context.startActivity(movieDetail);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(movie_list.get(position).title);
        holder.voterTextView.setText("(" + movie_list.get(position).voteCount + ")");
        float rating_score = (float)(movie_list.get(position).voteAverage/10)*5;
        holder.ratingTextView.setText(String.format("%.1f",rating_score));
        holder.ratingBar.setRating(rating_score);
        holder.releaseTextView.setText(movie_list.get(position).releaseDate.substring(0,4));

        String url = NetworkUtils.buildMovieUrl(movie_list.get(position).posterPath).toString();
        Glide.with(context).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        if(null == movie_list) return 0;
        return movie_list.size();
    }

}
