package org.papaorange.mymovielist.controller;

import org.papaorange.mymovielist.model.Movie;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetMyMovieListController {

	@RequestMapping("/mymovies")
	public Movie getMyMovieList() {

		Movie movie = new Movie();
		movie.setName("A Good Movie");
		movie.setRatingValue(8.8);
		movie.setImgUrl("http://www.baidu.com");
		movie.setSummaryText("Just a Moive");

		return movie;
	}

}
