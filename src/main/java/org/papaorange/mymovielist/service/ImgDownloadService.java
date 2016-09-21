package org.papaorange.mymovielist.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.papaorange.mymovielist.model.DoubanMovieInfo;
import org.papaorange.mymovielist.model.LocalMovieInfo;
import org.papaorange.mymovielist.model.MovieList;
import org.papaorange.mymovielist.utils.GetMovieDBJsonString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSON;

public class ImgDownloadService {
	private MovieList dbMvInfoList = new MovieList();
	private List<LocalMovieInfo> localMvInfoList = new ArrayList<LocalMovieInfo>();
	private static final Logger log = LoggerFactory.getLogger(ImgDownloadService.class);

	@Scheduled(fixedRate = 30 * 1000)
	public void updateMovieDBTask() throws InterruptedException, IOException {

		log.info("checking local movie img folder...");

		dbMvInfoList = JSON.parseObject(GetMovieDBJsonString.getJsonString(), MovieList.class);
		
	}
}
