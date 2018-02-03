package amri.k.mymovies;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import amri.k.mymovies.utilities.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    String MOVIE_TITLE = "title";
    String MOVIE_OVERVIEW = "overview";
    String MOVIE_RELEASE = "release_date";
    String MOVIE_POSTER = "poster_path";
    String MOVIE_VOTE_AVERAGE = "vote_average";
    String MOVIE_VOTE_COUNT = "vote_count";
    String MOVIE_BACK_DROP = "backdrop_path";

    //ID yang akan di binding ke MainActivity menggunakan butterknife
    @BindView(R.id.title_details) TextView titleTextView;
    @BindView(R.id.year_details) TextView releaseDateTextView;
    @BindView(R.id.rating_score_detail) TextView ratingTextView;
    @BindView(R.id.num_of_votes_detail) TextView voteCountTextView;
    @BindView(R.id.plot_synopsis) TextView overviewTextView;
    @BindView(R.id.average_vote) TextView voteAverageTextView;
    @BindView(R.id.iv_movie_poster_details) ImageView posterImageView;
    @BindView(R.id.iv_backdrop) ImageView backDropImageView;
    @BindView(R.id.rating_bar_detail) RatingBar ratingBar;
    @BindView(R.id.collapse_toolbar) CollapsingToolbarLayout collapsingToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_main);

        // memanggil inisiasi butterknife
        ButterKnife.bind(this);

        final Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = this.getIntent();

        if (intent != null && intent.hasExtra(MOVIE_TITLE)){
            actionBar.setTitle(intent.getStringExtra(MOVIE_TITLE) + " (" +
                    intent.getStringExtra(MOVIE_RELEASE).substring(0,4) + ")");
            collapsingToolbar.setTitle(intent.getStringExtra(MOVIE_TITLE) + " (" +
                    intent.getStringExtra(MOVIE_RELEASE).substring(0,4) + ")");
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.darker_gray));
            collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
            collapsingToolbar.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
            collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));

            titleTextView.setText(intent.getStringExtra(MOVIE_TITLE));
            releaseDateTextView.setText(intent.getStringExtra(MOVIE_RELEASE));
            voteCountTextView.setText("(" + String.valueOf(intent.getStringExtra(MOVIE_VOTE_COUNT)) + ")");
            overviewTextView.setText(intent.getStringExtra(MOVIE_OVERVIEW));
            voteAverageTextView.setText(intent.getStringExtra(MOVIE_VOTE_AVERAGE) + "/10");
            String url1 = NetworkUtils.buildMovieUrl(intent.getStringExtra(MOVIE_POSTER)).toString();
            Glide.with(this)
                    .load(url1)
                    .into(posterImageView);

            String url2 = NetworkUtils.buildMovieUrl(intent.getStringExtra(MOVIE_BACK_DROP)).toString();
            Glide.with(this)
                    .load(url2)
                    .into(backDropImageView);

            double voteAverage = Double.parseDouble(intent.getStringExtra(MOVIE_VOTE_AVERAGE));
            voteAverage = (voteAverage/10)*5;
            String rating = String.format("%.1f",voteAverage);
            voteAverage = Double.parseDouble(rating);

            ratingTextView.setText(rating);
            ratingBar.setRating((float)voteAverage);
            ratingBar.setStepSize((float)0.1);
        }

    }
}
