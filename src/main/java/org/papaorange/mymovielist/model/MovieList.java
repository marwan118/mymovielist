package org.papaorange.mymovielist.model;

import java.util.List;

public class MovieList {
	private List<DoubanMovieInfo> movieList;

	public MovieList(List<DoubanMovieInfo> movieList) {
		this.movieList = movieList;
	}

	public MovieList() {
		// TODO Auto-generated constructor stub
	}

	public List<DoubanMovieInfo> getMovieList() {
		return movieList;
	}

	public void setMovieList(List<DoubanMovieInfo> movieList) {
		this.movieList = movieList;
	}
}
