package org.papaorange.mymovielist.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.papaorange.mymovielist.model.LocalMovieInfo;
import org.papaorange.mymovielist.utils.AppConfigLoader;

public class LocalMovieInfoCollector {

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
			System.out.println(name);
			localMovieInfos.add(new LocalMovieInfo(name, "2011"));
		}
		return localMovieInfos;
	}

	public static void main(String[] args) {

		getLocalMovieInfo();

	}
}
