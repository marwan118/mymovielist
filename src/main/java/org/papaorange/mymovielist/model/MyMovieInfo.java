package org.papaorange.mymovielist.model;

import com.alibaba.fastjson.annotation.JSONField;

public class MyMovieInfo {

	private String name;

	private String imgUrl;

	private String refUrl;

	private String alias;
	
	private String localMvFileName;

	private String year;

	private String localImgFileName;

	private double ratingValue;

	private String summaryText;

	@JSONField(name = "title")
	public String getName() {
		return name;
	}

	@JSONField(name = "title")
	public void setName(String name) {
		this.name = name;
	}

	@JSONField(name = "sub_title")
	public String getAlias() {
		return alias;
	}

	@JSONField(name = "sub_title")
	public void setAlias(String alias) {
		this.alias = alias;
	}

	@JSONField(name = "img")
	public String getImgUrl() {
		return imgUrl;
	}

	@JSONField(name = "img")
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public double getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(double ratingValue) {
		this.ratingValue = ratingValue;
	}

	public String getSummaryText() {
		return summaryText;
	}

	public void setSummaryText(String summaryText) {
		this.summaryText = summaryText;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@JSONField(name = "url")
	public String getRefUrl() {
		return refUrl;
	}

	@JSONField(name = "url")
	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}

	@JSONField(name = "imgFileName")
	public String getLocalImgFileName() {
		return localImgFileName;
	}

	@JSONField(name = "imgFileName")
	public void setLocalImgFileName(String localImgFileName) {
		this.localImgFileName = localImgFileName;
	}

	@JSONField(name = "localMovieFileName")
	public String getLocalMvFileName() {
		return localMvFileName;
	}

	@JSONField(name = "localMovieFileName")
	public void setLocalMvFileName(String localMvFileName) {
		this.localMvFileName = localMvFileName;
	}

}
