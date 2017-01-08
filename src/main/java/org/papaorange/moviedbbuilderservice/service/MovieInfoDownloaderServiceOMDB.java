package org.papaorange.moviedbbuilderservice.service;

import java.net.URLEncoder;

import org.papaorange.moviedbbuilderservice.model.LocalMovieInfo;
import org.papaorange.moviedbbuilderservice.model.MyMovieInfo;
import org.papaorange.moviedbbuilderservice.model.OMDBItem;
import org.papaorange.moviedbbuilderservice.utils.HttpGetUtil;
import com.alibaba.fastjson.JSON;

public class MovieInfoDownloaderServiceOMDB
{

    public static OMDBItem queryMovieInfoFromOMDB(String title, String year, String plot, String response)
    {
	String params = "";
	OMDBItem item = null;
	String url = "http://www.omdbapi.com/?";
	params = "t=" + title.replace(" ", "%20") + "&y=" + year + "&plot=" + plot + "&r=" + response;
	String responseBody = "";
	try
	{
	    responseBody = HttpGetUtil.doGet(url + params);
	    System.err.println("get:" + url + params);
	    item = JSON.parseObject(responseBody, OMDBItem.class);
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	return item;
    }

    public static MyMovieInfo downloadMovieInfoFromOMDB(LocalMovieInfo info)
    {
	OMDBItem item = queryMovieInfoFromOMDB(info.getMovieName(), info.getYear(), "short", "json");

	MyMovieInfo ret = new MyMovieInfo();

	ret.setLocalMvFileName(info.getMoviePath());
	ret.setPlayableFilePath(info.getPlayerAblePath());
	ret.setHasSubTitle(info.hasSubtitle());
	ret.setImgUrl(item.getPoster());
	ret.setImdbID(item.getImdbID());
	return ret;
    }
}
