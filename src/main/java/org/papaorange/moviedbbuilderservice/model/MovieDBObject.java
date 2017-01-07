package org.papaorange.moviedbbuilderservice.model;

import java.util.List;

public class MovieDBObject
{
    private List<MyMovieInfo> movieList;

    public MovieDBObject()
    {

    }

    public MovieDBObject(List<MyMovieInfo> movieList)
    {
	this.movieList = movieList;
    }

    public List<MyMovieInfo> getMovieList()
    {
	return movieList;
    }

    public void setMovieList(List<MyMovieInfo> movieList)
    {
	this.movieList = movieList;
    }

}
