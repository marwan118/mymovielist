package org.papaorange.moviedbbuilderservice.controller;

import java.io.FileInputStream;
import java.io.IOException;
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
	String movieDBPath = AppConfigLoader.getProp("MovieDBOutputPath") + "movieDB.json";
	return JSON.parseObject(new FileInputStream(movieDBPath), MovieDBObject.class);
    }

}
