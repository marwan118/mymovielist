package org.papaorange.moviedbbuilderservice.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.papaorange.moviedbbuilderservice.model.MovieDBObject;
import org.papaorange.moviedbbuilderservice.service.MovieDBBuilderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
public class GetMyMovieListController
{

    private static final Logger log = Logger.getLogger(GetMyMovieListController.class);

    
    @CrossOrigin
    @RequestMapping("/mymovies")
    public MovieDBObject getMyMovieList() throws IOException
    {

	try
	{
	    MovieDBBuilderService.updateMovieDBTask();
	}
	catch (InterruptedException e)
	{
	    log.error("更新电影数据库失败...");
	    e.printStackTrace();
	}
	String jsonString = "";
	BufferedReader reader = null;
	FileInputStream in = new FileInputStream("db/movieDB.json");

	try
	{
	    InputStreamReader isr = new InputStreamReader(in, "UTF-8");
	    reader = new BufferedReader(isr);
	    String line = null;
	    while ((line = reader.readLine()) != null)
	    {
		jsonString += line;
	    }

	}
	finally
	{
	    reader.close();
	}

	return JSON.parseObject(jsonString, MovieDBObject.class);
    }

}
