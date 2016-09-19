package org.papaorange.mymovielist.service;

import org.papaorange.mymovielist.model.DoubanMovieInfo;
import org.papaorange.mymovielist.utils.AppConfigLoader;
import org.papaorange.mymovielist.utils.HttpGetUtil;

public class DoubanMovieInfoCollector {

	public static DoubanMovieInfo getDoubanMovieInfoObjectCollectionByName(String name) {
		// toDO
		String doubanMovieInfo = "";

		try {

			doubanMovieInfo = HttpGetUtil.doGet(AppConfigLoader.getProp("MovieDBURL") + name.replace(" ", "%20"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDoubanMovieInfoStringCollectionByName(String name) {

		String doubanMovieInfo = "";

		try {

			doubanMovieInfo = HttpGetUtil.doGet(AppConfigLoader.getProp("MovieDBURL") + name.replace(" ", "%20"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doubanMovieInfo;
	}

}
