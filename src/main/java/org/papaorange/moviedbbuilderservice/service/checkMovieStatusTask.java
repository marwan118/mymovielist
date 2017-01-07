package org.papaorange.moviedbbuilderservice.service;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.papaorange.moviedbbuilderservice.utils.AppConfigLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class checkMovieStatusTask
{
    private static final Logger log = Logger.getLogger(checkMovieStatusTask.class);

    private static String lastUpdateTime = "";

    private static boolean init = true;

    @Scheduled(fixedRate = 5000)
    public static boolean checkIfMovieDirUpdate()
    {
	boolean updated = false;
	if (init)
	{
	    lastUpdateTime = getLastMovieDirModifyTime();
	    init = false;
	}

	if (!lastUpdateTime.equals(getLastMovieDirModifyTime()))
	{
	    updated = true;
	    log.info("检测到Movie文件夹变化...");
	    lastUpdateTime = getLastMovieDirModifyTime();
	    try
	    {
		MovieDBBuilderService.updateMovieDBTask();
	    }
	    catch (IOException e)
	    {
		e.printStackTrace();
	    }
	}
	return updated;
    }

    private static String getLastMovieDirModifyTime()
    {
	String movieDir = null;
	try
	{
	    movieDir = AppConfigLoader.getProp("LocalMoviePath");
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Long lastModified = FileUtils.getFile(movieDir).lastModified();
	Date date = new Date(lastModified);
	return date.toString();
    }
}
