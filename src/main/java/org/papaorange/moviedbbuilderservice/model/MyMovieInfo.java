package org.papaorange.moviedbbuilderservice.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class MyMovieInfo
{

    @JSONField(name = "title")
    private String name;

    @JSONField(name = "url")
    private String refUrl;

    @JSONField(name = "sub_title")
    private String alias;

    @JSONField(name = "year")
    private String year;

    private String poster;

    private String imdbID;

    private String localMovieName;

    private List<String> playableFilePath;

    private String localImgFileName;

    private double ratingValue;

    private String summaryText;

    private boolean hasSubtitle;

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public String getAlias()
    {
	return alias;
    }

    public void setAlias(String alias)
    {
	this.alias = alias;
    }

    public String getImgUrl()
    {
	return poster;
    }

    public void setImgUrl(String imgUrl)
    {
	this.poster = imgUrl;
    }

    public double getRatingValue()
    {
	return ratingValue;
    }

    public void setRatingValue(double ratingValue)
    {
	this.ratingValue = ratingValue;
    }

    public String getSummaryText()
    {
	return summaryText;
    }

    public void setSummaryText(String summaryText)
    {
	this.summaryText = summaryText;
    }

    public String getYear()
    {
	return year;
    }

    public void setYear(String year)
    {
	this.year = year;
    }

    public String getRefUrl()
    {
	return refUrl;
    }

    public void setRefUrl(String refUrl)
    {
	this.refUrl = refUrl;
    }

    public String getLocalImgFileName()
    {
	return localImgFileName;
    }

    public void setLocalImgFileName(String localImgFileName)
    {
	this.localImgFileName = localImgFileName;
    }

    public String getLocalMvFileName()
    {
	return localMovieName;
    }

    public void setLocalMvFileName(String localMvFileName)
    {
	this.localMovieName = localMvFileName;
    }

    public List<String> getPlayableFilePath()
    {
	return playableFilePath;
    }

    public void setPlayableFilePath(List<String> playableFilePath)
    {
	this.playableFilePath = playableFilePath;
    }

    public boolean isHasSubTitle()
    {
	return hasSubtitle;
    }

    public void setHasSubTitle(boolean hasSubTitle)
    {
	this.hasSubtitle = hasSubTitle;
    }

    public String getImdbID()
    {
	return imdbID;
    }

    public void setImdbID(String imdbID)
    {
	this.imdbID = imdbID;
    }

}
