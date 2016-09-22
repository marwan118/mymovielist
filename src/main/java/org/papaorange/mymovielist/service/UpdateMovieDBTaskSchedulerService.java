package org.papaorange.mymovielist.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.papaorange.mymovielist.model.MyMovieInfo;
import org.papaorange.mymovielist.model.LocalMovieInfo;
import org.papaorange.mymovielist.model.MovieList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class UpdateMovieDBTaskSchedulerService {

	private List<MyMovieInfo> dbMvInfoList = new ArrayList<MyMovieInfo>();
	private List<LocalMovieInfo> localMvInfoList = new ArrayList<LocalMovieInfo>();
	private boolean firstUpdate = true;
	private boolean localMovieUpdate = false;
	private static final Logger log = LoggerFactory.getLogger(UpdateMovieDBTaskSchedulerService.class);

	@Scheduled(fixedRate = 30 * 1000)
	public void updateMovieDBTask() throws InterruptedException {

		log.info("checking local movie folder...");

		List<LocalMovieInfo> mvList = MovieInfoCollectService.getLocalMovieInfo();
		if (firstUpdate) {
			localMvInfoList = mvList;
			firstUpdate = false;
			localMovieUpdate = true;
		}
		if (localMvInfoList.size() == mvList.size()) {
			for (int i = 0; i < mvList.size(); i++) {
				if (localMvInfoList.get(i).getFileName().equals(mvList.get(i).getFileName())) {
					continue;
				} else {
					localMovieUpdate = true;
				}
			}
		} else {
			localMovieUpdate = true;
		}
		if (localMovieUpdate == false) {
			return;
		}

		log.info("local movie folder changed,updating moviedb...");

		dbMvInfoList.clear();
		localMovieUpdate = false;

		for (LocalMovieInfo mv : mvList) {
			String mvName = mv.getMovieName();
			String mvYear = mv.getYear();
			if (mvName.equals("unknown") && mvYear.equals("unknown")) {
				continue;
			}
			MyMovieInfo info = MovieInfoCollectService.getDoubanMovieInfoObjectCollectionByName(mv);
			if (info == null) {
				continue;
			}
			try {
				MovieInfoCollectService.updateDetailInfo(info);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dbMvInfoList.add(info);
		}

		String dbString = JSON.toJSONString(new MovieList(dbMvInfoList));

		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream("db/movieDB.json");
			osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(dbString);
			osw.flush();
			osw.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			}

		}
	}

}
