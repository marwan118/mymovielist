package org.papaorange.mymovielist.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.papaorange.mymovielist.model.DoubanMovieInfo;
import org.papaorange.mymovielist.model.LocalMovieInfo;
import org.papaorange.mymovielist.utils.AppConfigLoader;
import org.papaorange.mymovielist.utils.HttpGetUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class MovieInfoCollector {
	public static List<LocalMovieInfo> getLocalMovieInfo() {

		List<LocalMovieInfo> localMovieInfos = new ArrayList<LocalMovieInfo>();
		String filePath = "";
		try {
			filePath = AppConfigLoader.getProp("LocalMoviePath");
		} catch (IOException e) {
			System.err.println("未找到配置文件./config/application.properties");
			e.printStackTrace();
		}

		File movieDir = new File(filePath);

		for (String name : movieDir.list()) {
			localMovieInfos.add(new LocalMovieInfo(name));
		}
		return localMovieInfos;
	}

	public static DoubanMovieInfo getDoubanMovieInfoObjectCollectionByName(String name) {

		String doubanMovieInfo = "";
		try {

			doubanMovieInfo = HttpGetUtil.doGet(AppConfigLoader.getProp("MovieDBURL") + name.replace(" ", "%20"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<DoubanMovieInfo> list = JSON.parseObject(doubanMovieInfo, new TypeReference<List<DoubanMovieInfo>>() {
		});

		int mvIdx = 0;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getAlias().toLowerCase().trim().equals(name.toLowerCase().trim())) {
				mvIdx = i;
			}
		}

		if (list.size() == 0) {
			return null;
		} else {

			return list.get(mvIdx);
		}
	}

}
