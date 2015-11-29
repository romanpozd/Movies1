package com.roman.movies.Utils;

import java.util.Comparator;

import com.roman.movies.Model.Movie;

public class MoviesSortByYear implements Comparator<Movie> {

	@Override
	public int compare(Movie mov1, Movie mov2) {
		return mov2.getYear() - mov1.getYear();
	}

}
