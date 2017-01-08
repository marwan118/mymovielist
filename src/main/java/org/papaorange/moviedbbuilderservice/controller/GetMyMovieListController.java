package org.papaorange.moviedbbuilderservice.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.papaorange.moviedbbuilderservice.model.MovieDBObject;
import org.papaorange.moviedbbuilderservice.utils.AppConfigLoader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
public class GetMyMovieListController
{

    // private static final Logger log =
    // Logger.getLogger(GetMyMovieListController.class);

    @CrossOrigin
    @RequestMapping("/mymovies")
    public MovieDBObject getMyMovieList() throws IOException
    {

	String jsonString = "";
	BufferedReader reader = null;
	String movieDBPath = AppConfigLoader.getProp("MovieDBOutputPath") + "movieDB.json";

	FileInputStream in = new FileInputStream(movieDBPath);

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
