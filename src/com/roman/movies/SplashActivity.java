package com.roman.movies;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import com.roman.movies.Model.Movie;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

	// URLs
	private static final String moviesURL = "http://x-mode.co.il/exam/allMovies/allMovies.txt";
	private static final String adsURL = "http://x-mode.co.il/exam/allMovies/generalDeclaration.txt";

	// Volley requests count down
	private int requestCount = 0;
	private CountDownLatch requestCountDown;

	// List of movies from volley request
	private ArrayList<Movie> movies;

	private ProgressBar progressBar;

	private Bitmap adBitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);

		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		progressBar.setVisibility(View.VISIBLE);

		MoviesListRequest();
		AdImageURLRequest();

		requestCountDown = new CountDownLatch(requestCount);
		final Handler threadHandler = new Handler();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					requestCountDown.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				threadHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						progressBar.setVisibility(View.GONE);
						Intent intent = new Intent(SplashActivity.this, AdActivity.class);
						intent.putExtra("adImage", adBitmap);
						startActivityForResult(intent, 100);
					}

				}, 1000);
			}
		}).start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 100){
			Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
			mainIntent.putParcelableArrayListExtra("moviesList", movies);
			startActivity(mainIntent);
			finish();
		}
	}

	private void getAdBitmap(String url) {
		requestCount++;
		parseJSON parseJSON = new parseJSON();
		parseJSON.getImage(url, new parseJSON.VolleyImageRequest() {

			@Override
			public void onSuccess(Bitmap bitmap) {
				adBitmap = bitmap;
				requestCountDown.countDown();
			}
		});
	}

	private void MoviesListRequest() {
		requestCount++;
		parseJSON json = new parseJSON();
		json.ParseMoviesList(moviesURL, new parseJSON.VolleyCallBackMovies() {

			@Override
			public void onSuccess(ArrayList<Movie> moviesList) {
				movies = moviesList;
				requestCountDown.countDown();
			}
		});
	}

	private void AdImageURLRequest() {
		requestCount++;
		parseJSON json = new parseJSON();
		json.ParseAdImage(adsURL, new parseJSON.VolleyCallBackAds() {
			
			@Override
			public void onSuccess(String imgURL) {
				getAdBitmap(modifyString(imgURL));
				requestCountDown.countDown();
			}
		});
	}
	
	private String modifyString(String string){
		String[] separate = string.split(".jpg");
		return separate[0] + ".jpg";
	}
}
