/**
 * 
 */
package com.roman.movies;

import java.util.Collections;
import java.util.List;
import com.roman.movies.Adapters.moviesListAdapter;
import com.roman.movies.Model.Movie;
import com.roman.movies.Utils.MovieComparator;
import com.roman.movies.Utils.MoviesSortByTitle;
import com.roman.movies.Utils.MoviesSortByYear;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

	private Toolbar toolbar;
	private List<Movie> movies;
	private RecyclerView moviesRecycler;
	private moviesListAdapter adapter;
	private Spinner sortSpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainactivity_screen);

		toolbar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(toolbar);
		Intent intent = getIntent();
		if (intent.getExtras() != null)
			movies = intent.getParcelableArrayListExtra("moviesList");

		moviesRecycler = (RecyclerView) findViewById(R.id.recycler_view);
		moviesRecycler.setLayoutManager(new LinearLayoutManager(this));
		moviesRecycler.setHasFixedSize(true);
		adapter = new moviesListAdapter(movies);
		moviesRecycler.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.sort_option);
		sortSpinner = (Spinner) item.getActionView();

		ArrayAdapter<CharSequence> sortOptionsAdapter = ArrayAdapter.createFromResource(this, R.array.sort_array,
				android.R.layout.simple_spinner_item);
		sortSpinner.setAdapter(sortOptionsAdapter);
		sortSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					Collections.sort(movies, new MovieComparator(new MoviesSortByTitle()));
					adapter = new moviesListAdapter(movies);
					moviesRecycler.setAdapter(adapter);
					break;
				case 1:
					Collections.sort(movies, new MovieComparator(new MoviesSortByYear()));
					adapter = new moviesListAdapter(movies);
					moviesRecycler.setAdapter(adapter);
					break;
				case 2:
					// TODO sort by category
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		return true;
	}

}
