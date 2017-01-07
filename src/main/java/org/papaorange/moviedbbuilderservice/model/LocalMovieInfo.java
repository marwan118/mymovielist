package org.papaorange.moviedbbuilderservice.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.papaorange.moviedbbuilderservice.utils.LocalMovieInfoParser;

public class LocalMovieInfo
{
    private String moviename;
    private String year;
    private String filename;
    private String moviepath;
    private List<String> filepath = new ArrayList<String>();;

    private boolean hasSubtitle = false;

    private List<String> playerAblePath = new ArrayList<String>();

    public LocalMovieInfo(String filepath)
    {
	this.filename = filepath.substring(filepath.lastIndexOf("/") + 1);
	this.moviename = LocalMovieInfoParser.parseMovieName(filename);
	this.year = LocalMovieInfoParser.parseMovieYear(filename);
	this.moviepath = filepath;
	Collection<File> files = FileUtils.listFiles(new File(filepath), FileFilterUtils.suffixFileFilter(""), null);
	for (File file : files)
	{
	    if ((file.length() / 1024 / 1024) > 500)
	    {
		String temp = file.getAbsolutePath();
		this.filepath.add(temp);
		playerAblePath.add("smb://" + "xiazaibao_fb42/" + temp.substring(temp.lastIndexOf("=") + 1));
		// System.out.println(playerAblePath);
	    }
	    if (file.getName().contains(".srt") || file.getName().contains(".ass") || file.getName().contains(".ssa"))
	    {
		hasSubtitle = true;
	    }
	}
    }

    public String getMovieName()
    {
	return moviename;
    }

    public void setMovieName(String name)
    {
	this.moviename = name;
    }

    public String getYear()
    {
	return year;
    }

    public void setYear(String year)
    {
	this.year = year;
    }

    public String getFileName()
    {
	return filename;
    }

    public void setFileName(String fileName)
    {
	this.filename = fileName;
    }

    public String getMoviePath()
    {
	return moviepath;
    }

    public void setMoviePath(String filepath)
    {
	this.moviepath = filepath;
    }

    public List<String> getPlayerAblePath()
    {
	return playerAblePath;
    }

    public List<String> getFilepath()
    {
	return filepath;
    }

    public void setFilepath(List<String> filepath)
    {
	this.filepath = filepath;
    }

    public void setPlayerAblePath(List<String> playerAblePath)
    {
	this.playerAblePath = playerAblePath;
    }

    public boolean hasSubtitle()
    {
	return hasSubtitle;
    }
}
