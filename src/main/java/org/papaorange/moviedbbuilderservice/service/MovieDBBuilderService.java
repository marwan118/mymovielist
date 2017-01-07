package org.papaorange.moviedbbuilderservice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.papaorange.moviedbbuilderservice.model.LocalMovieInfo;
import org.papaorange.moviedbbuilderservice.model.MovieDBObject;
import org.papaorange.moviedbbuilderservice.model.MyMovieInfo;

import com.alibaba.fastjson.JSON;

public class MovieDBBuilderService
{

    private static final Logger log = Logger.getLogger(MovieDBBuilderService.class);

    public static void updateMovieDBTask() throws FileNotFoundException, IOException
    {

	MovieDBObject movieDBObject = null;
	List<MyMovieInfo> mvInfoListFromDB = new ArrayList<MyMovieInfo>();

	log.info("获取本地电影目录列表...");
	Map<String, LocalMovieInfo> movieToCollect = MovieInfoCollectService.getLocalMovieInfoAsMap();
	log.info("本地共有电影" + movieToCollect.size() + "部...");
	if (new File("db/movieDB.json").exists())
	{
	    log.info("找到movieDB.json文件,追加解析模式...");
	    movieDBObject = JSON.parseObject(new FileInputStream("db/movieDB.json"), MovieDBObject.class);
	    mvInfoListFromDB = movieDBObject.getMovieList();

	    List<MyMovieInfo> tobeDeleteItemList = new ArrayList<MyMovieInfo>();
	    for (MyMovieInfo myMovieInfo : mvInfoListFromDB)
	    {
		String key = myMovieInfo.getLocalMvFileName();
		if (movieToCollect.containsKey(key))
		{
		    movieToCollect.remove(key);
		}
		else
		{
		    tobeDeleteItemList.add(myMovieInfo);
		}
	    }

	    for (MyMovieInfo info : tobeDeleteItemList)
	    {
		mvInfoListFromDB.remove(info);
		FileUtils.forceDelete(new File("./db/" + info.getLocalImgFileName()));
	    }

	}
	else
	{
	    log.info("movieDB.json不存在,全量解析模式...");
	}

	int i = 1;
	for (LocalMovieInfo mv : movieToCollect.values())
	{
	    String mvName = mv.getMovieName();
	    String mvYear = mv.getYear();
	    if (mvName.equals("unknown") && mvYear.equals("unknown"))
	    {
		continue;
	    }
	    log.info("正在从网络下载电影信息:" + (i++) + "/" + movieToCollect.size() + " 电影名称:[" + mvName + "]");

	    MyMovieInfo info = new MyMovieInfo();
	    info = MovieInfoCollectService.getDoubanMovieInfoObjectCollectionByName(mv);

	    if (info == null)
	    {
		continue;
	    }
	    try
	    {
		MovieInfoCollectService.updateDetailInfo(info);
	    }
	    catch (Exception e)
	    {
		e.printStackTrace();
	    }
	    mvInfoListFromDB.add(info);
	}

	String dbString = JSON.toJSONString(new MovieDBObject(mvInfoListFromDB));
	log.info("输出movie.DB,总共电影条目数:" + mvInfoListFromDB.size());
	log.info("下载字幕...");
	for (LocalMovieInfo movieInfo : MovieInfoCollectService.getLocalMovieInfoAsMap().values())
	{
	    if (movieInfo.hasSubtitle())
	    {
		log.info("[" + movieInfo.getFileName() + "]" + "字幕存在...略过");
	    }
	    else
	    {
		List<String> mvPaths = movieInfo.getFilepath();
		for (String mvPath : mvPaths)
		{
		    SubDownloaderService.downloadSub(mvPath);
		}
	    }
	}
	log.info("运行结束退出...");

	OutputStreamWriter osw = null;
	FileOutputStream fos = null;

	try
	{
	    fos = new FileOutputStream("db/movieDB.json");
	    osw = new OutputStreamWriter(fos, "UTF-8");
	    osw.write(dbString);
	    osw.flush();
	    osw.close();
	    fos.close();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	finally
	{
	    try
	    {
		if (osw != null)
		{
		    osw.close();
		}
		if (fos != null)
		{
		    fos.close();
		}
	    }
	    catch (IOException exception)
	    {
		exception.printStackTrace();
	    }

	}
    }
}
