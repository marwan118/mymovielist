package org.papaorange.mymovielist.model;

import java.util.List;

public class MovieList {
	private List<MyMovieInfo> movieList;

	public MovieList() {

	}

	public MovieList(List<MyMovieInfo> movieList) {
		this.movieList = movieList;
	}

	public List<MyMovieInfo> getMovieList() {
		return movieList;
	}

	public void setMovieList(List<MyMovieInfo> movieList) {
		this.movieList = movieList;
	}
}
