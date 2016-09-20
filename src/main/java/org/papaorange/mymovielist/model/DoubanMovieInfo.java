package org.papaorange.mymovielist.model;

import com.alibaba.fastjson.annotation.JSONField;

public class DoubanMovieInfo {

	private String name;

	private String imgUrl;

	private String alias;

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

}
