package org.papaorange.moviedbbuilderservice.service;

import org.papaorange.moviedbbuilderservice.model.OMDBItem;
import org.papaorange.moviedbbuilderservice.utils.HttpGetUtil;
import com.alibaba.fastjson.JSON;

public class MovieInfoDownloaderService
{

    public static OMDBItem queryMovieInfoFromOMDB(String title, String year, String plot, String response)
    {
	String params = "";
	OMDBItem item = null;
	String url = "http://www.omdbapi.com/?";
	params = "t=" + title + "&y=" + year + "&plot=" + plot + "&r=" + response;
	String responseBody = "";
	try
	{
	    responseBody = HttpGetUtil.doGet(url + params);
	    item = JSON.parseObject(responseBody, OMDBItem.class);
	}
	catch (Exception e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return item;
    }
}
