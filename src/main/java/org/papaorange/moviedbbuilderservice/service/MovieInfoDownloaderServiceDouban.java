package org.papaorange.moviedbbuilderservice.service;

import java.util.List;

import org.papaorange.moviedbbuilderservice.model.MyMovieInfo;
import org.papaorange.moviedbbuilderservice.utils.AppConfigLoader;
import org.papaorange.moviedbbuilderservice.utils.HttpGetUtil;

import com.alibaba.fastjson.JSON;

public class MovieInfoDownloaderServiceDouban
{
    public static MyMovieInfo updateMovieInfoFormDouban(MyMovieInfo info)
    {
	String doubanMovieInfo = "";
	try
	{
	    doubanMovieInfo = HttpGetUtil.doGet(AppConfigLoader.getProp("MovieDBURL") + info.getImdbID());
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	List<MyMovieInfo> list = JSON.parseArray(doubanMovieInfo, MyMovieInfo.class);
	if (list.size() != 1)
	{
	    return null;
	}
	else
	{
	    MyMovieInfo temp = list.get(0);
	    info.setAlias(temp.getAlias());
	    info.setRefUrl(temp.getRefUrl());
	    info.setName(temp.getName());
	    info.setYear(temp.getYear());
	    return info;
	}
    }

    public static void main(String[] args)
    {
	// updateMovieInfoFormDouban("tt4080728");
    }
}
