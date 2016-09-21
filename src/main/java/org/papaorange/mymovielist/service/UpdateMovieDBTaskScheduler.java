package org.papaorange.mymovielist.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import org.papaorange.mymovielist.model.DoubanMovieInfo;
import org.papaorange.mymovielist.model.LocalMovieInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class UpdateMovieDBTaskScheduler {

	private List<DoubanMovieInfo> dbMvInfoList = new ArrayList<DoubanMovieInfo>();
	private List<LocalMovieInfo> localMvInfoList = new ArrayList<LocalMovieInfo>();
	private boolean firstUpdate = true;
	private boolean localMvNotChange = true;

	@Scheduled(fixedRate = 30 * 1000)
	public void updateMovieDBTask() throws InterruptedException {

		System.err.println("checking local movie folder...");
		List<LocalMovieInfo> mvList = MovieInfoCollector.getLocalMovieInfo();
		if (firstUpdate) {
			localMvInfoList = mvList;
			firstUpdate = false;
			localMvNotChange = false;
		}
		if (localMvInfoList.size() == mvList.size()) {
			for (int i = 0; i < mvList.size(); i++) {
				if (localMvInfoList.get(i).getFileName().equals(mvList.get(i).getFileName())) {
					continue;
				} else {
					localMvNotChange = true;
				}
			}
		} else {
			localMvNotChange = true;
		}
		if (localMvNotChange == false) {
			return;
		}
		System.err.println("local movie folder changed,updating moviedb...");
		dbMvInfoList.clear();

		for (LocalMovieInfo mv : mvList) {
			String mvName = mv.getMovieName();
			String mvYear = mv.getYear();
			if (mvName.equals("unknown") && mvYear.equals("unknown")) {
				continue;
			}
			DoubanMovieInfo info = MovieInfoCollector.getDoubanMovieInfoObjectCollectionByName(mv);
			if (info == null) {
				continue;
			}
			MovieInfoCollector.updateDetailInfo(info);
			dbMvInfoList.add(info);
		}

		String dbString = JSON.toJSONString(dbMvInfoList);

		OutputStreamWriter osw = null;
		FileOutputStream fos = null;

		FileLock lock = null;
		try {
			fos = new FileOutputStream("movieDB.json");
			lock = fos.getChannel().lock();
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
				lock.release();
			} catch (IOException exception) {
			}

		}
	}

}
