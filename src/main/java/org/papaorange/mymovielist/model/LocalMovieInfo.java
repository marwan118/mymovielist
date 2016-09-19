package org.papaorange.mymovielist.model;

import org.papaorange.mymovielist.utils.LocalMovieInfoParser;

public class LocalMovieInfo {
	private String moviename;
	private String year;
	private String filename;

	public LocalMovieInfo(String filename) {
		// String nameAndYear =
		// LocalMovieInfoParser.parseMovieNameAndYear(filename);
		this.moviename = LocalMovieInfoParser.parseMovieName(filename);
		this.year = LocalMovieInfoParser.parseMovieYear(filename);
		this.filename = filename;
	}

	public String getMovieName() {
		return moviename;
	}

	public void setMovieName(String name) {
		this.moviename = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getFileName() {
		return filename;
	}

	public void setFileName(String fileName) {
		this.filename = fileName;
	}

}
