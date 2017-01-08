package org.papaorange.moviedbbuilderservice.model;

import com.alibaba.fastjson.annotation.JSONField;

public class OMDBItem
{
    @JSONField(name = "Title")
    private String title;

    @JSONField(name = "Year")
    private String year;

    @JSONField(name = "Rated")
    private String rated;

    @JSONField(name = "Released")
    private String released;

    @JSONField(name = "Runtime")
    private String runtime;

    @JSONField(name = "Genre")
    private String genre;

    @JSONField(name = "Director")
    private String director;

    @JSONField(name = "Writer")
    private String writer;

    @JSONField(name = "Actors")
    private String actors;

    @JSONField(name = "Plot")
    private String plot;

    @JSONField(name = "Language")
    private String language;

    @JSONField(name = "Country")
    private String country;

    @JSONField(name = "Awards")
    private String awards;

    @JSONField(name = "Poster")
    private String poster;

    @JSONField(name = "Metascore")
    private String metascore;

    @JSONField(name = "imdbRating")
    private String imdbRating;

    @JSONField(name = "imdbVotes")
    private String imdbVotes;

    @JSONField(name = "imdbID")
    private String imdbID;

    @JSONField(name = "Type")
    private String type;

    @JSONField(name = "Response")
    private String response;

    public String getTitle()
    {
	return title;
    }

    public String getYear()
    {
	return year;
    }

    public String getRated()
    {
	return rated;
    }

    public String getReleased()
    {
	return released;
    }

    public String getRuntime()
    {
	return runtime;
    }

    public String getGenre()
    {
	return genre;
    }

    public String getDirector()
    {
	return director;
    }

    public String getWriter()
    {
	return writer;
    }

    public String getActors()
    {
	return actors;
    }

    public String getPlot()
    {
	return plot;
    }

    public String getLanguage()
    {
	return language;
    }

    public String getCountry()
    {
	return country;
    }

    public String getAwards()
    {
	return awards;
    }

    public String getPoster()
    {
	return poster;
    }

    public String getMetascore()
    {
	return metascore;
    }

    public String getImdbRating()
    {
	return imdbRating;
    }

    public String getImdbVotes()
    {
	return imdbVotes;
    }

    public String getImdbID()
    {
	return imdbID;
    }

    public String getType()
    {
	return type;
    }

    public String getResponse()
    {
	return response;
    }
}
