package org.papaorange.mymovielist.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	
	private static final Logger log = LoggerFactory.getLogger(UpdateMovieDBTaskSchedulerService.class);

	@Scheduled(fixedRate = 30 * 1000)
	public void updateMovieDBTask() throws InterruptedException, FileNotFoundException, IOException {

		
		 MovieList myMovieListFromDB = new MovieList();
		 List<MyMovieInfo> mvInfoListFromDB = new ArrayList<MyMovieInfo>();
		 boolean localMovieUpdate = false;
	
		 
		log.info("checking local movie folder...");
	
		List<LocalMovieInfo> mvList = MovieInfoCollectService.getLocalMovieInfo();
		if (new File("db/movieDB.json").exists()) {
			myMovieListFromDB = JSON.parseObject(new FileInputStream("db/movieDB.json"), MovieList.class);
			mvInfoListFromDB = myMovieListFromDB.getMovieList();

			List<String> dbMvNames = new ArrayList<String>();

			for (MyMovieInfo item : mvInfoListFromDB) {
				dbMvNames.add(item.getLocalMvFileName());
			}

			int count = mvList.size();
			if (dbMvNames.size() == mvList.size()) {
				for (int i = 0; i < count; i++) {
					if (dbMvNames.contains(mvList.get(i).getMovieName())) {
						dbMvNames.remove(mvList.get(i).getMovieName());
						continue;
					} else {
						localMovieUpdate = true;
					}
				}

				if (dbMvNames.size() != 0) {
					localMovieUpdate = true;
				}
			} else {
				localMovieUpdate = true;
			}
		}

		if (localMovieUpdate == false && new File("db/movieDB.json").exists()) {
			if (localMovieUpdate == false) {
				log.error("LocalMovie not updated...skip");
			}

			return;
		}
		if (new File("db/movieDB.json").exists() == false) {
			log.error("LocalMovieDB does not Exist...Init");
		}
		for (LocalMovieInfo mv : mvList) {
			String mvName = mv.getMovieName();
			String mvYear = mv.getYear();
			if (mvName.equals("unknown") && mvYear.equals("unknown")) {
				continue;
			}
			log.error("Update movie: [" + mvName + "] from internet...");

			MyMovieInfo info = new MyMovieInfo(); 
			info = MovieInfoCollectService.getDoubanMovieInfoObjectCollectionByName(mv);
			

			if (info == null) {
				continue;
			}
			try {
				
				MovieInfoCollectService.updateDetailInfo(info);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mvInfoListFromDB.add(info);
		}

		String dbString = JSON.toJSONString(new MovieList(mvInfoListFromDB));

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
