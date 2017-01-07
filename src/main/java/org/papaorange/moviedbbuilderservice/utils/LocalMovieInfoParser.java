package org.papaorange.moviedbbuilderservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalMovieInfoParser {
	public static String parseMovieYear(String filename) {

		int yearIndex = getYearIndexInFilenameString(filename);
		String year = "";
		try {
			year = filename.substring(yearIndex, yearIndex + 4);
		} catch (Exception e) {
			return "unknown";
		}
		return year;
	}

	public static String parseMovieName(String filename) {
		int yearIndex = getYearIndexInFilenameString(filename);
		String name = "";
		try {
			name = filename.substring(0, yearIndex).replace(".", " ").trim();
		} catch (Exception e) {
			return "unknown";
		}

		return name;
	}

	private static int getYearIndexInFilenameString(String filename) {
		String pattern = "(19|20)[0-9][0-9]";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(filename);
		if (m.find()) {
			// System.out.println("Found value: " + m.group(0));
			return m.start();
		} else {
			// System.out.println("NO MATCH");
			return -1;

		}
	}

//	public static void main(String[] args) {
//		System.out.println(
//				parseMovieName("13.Hours.The.Secret.Soldiers.Of.Benghazi.2016.1080p.BluRay.x264-Replica[rarbg]"));
//		System.out.println(
//				parseMovieYear("13.Hours.The.Secret.Soldiers.Of.Benghazi.2016.1080p.BluRay.x264-Replica[rarbg]"));
//	}
}
