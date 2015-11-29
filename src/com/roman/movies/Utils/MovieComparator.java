package com.roman.movies.Utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.roman.movies.Model.Movie;

public class MovieComparator implements Comparator<Movie>{

	private List<Comparator<Movie>> comparatorList;
	
	public MovieComparator(Comparator<Movie> moviesSortByTitle) {
		this.comparatorList = Arrays.asList(moviesSortByTitle);
	}
	@Override
	public int compare(Movie mov1, Movie mov2) {
		for (Comparator<Movie> comparator : comparatorList){
			int result  = comparator.compare(mov1, mov2);
			if (result != 0)
				return result;
		}
		return 0;
	}

}
