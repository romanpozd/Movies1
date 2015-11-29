package com.roman.movies.Utils;

import java.util.Comparator;

import com.roman.movies.Model.Movie;

public class MoviesSortByCategory implements Comparator<Movie>{

	@Override
	public int compare(Movie mov1, Movie mov2) {
		return mov1.getCategory().compareTo(mov2.getCategory());
	}
	

}
