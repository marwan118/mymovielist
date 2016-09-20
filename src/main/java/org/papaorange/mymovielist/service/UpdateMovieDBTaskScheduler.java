package org.papaorange.mymovielist.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

	@Scheduled(fixedRate = 60 * 1000)
	public void updateMovieDBTask() throws InterruptedException {
		System.err.println("Updating movieDB.json...");
		dbMvInfoList.clear();
		List<LocalMovieInfo> mvList = MovieInfoCollector.getLocalMovieInfo();
		for (LocalMovieInfo mv : mvList) {
			String mvName = mv.getMovieName();
			String mvYear = mv.getYear();
			if (mvName.equals("unknown") && mvYear.equals("unknown")) {
				continue;
			}
			DoubanMovieInfo info = MovieInfoCollector.getDoubanMovieInfoObjectCollectionByName(mv.getMovieName());
			if(info==null)
			{
				continue;
			}
			MovieInfoCollector.updateDetailInfo(info);
			dbMvInfoList.add(info);
		}

		String dbString = JSON.toJSONString(dbMvInfoList);

		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("movieDB.json");
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
			}

		}
	}

}
