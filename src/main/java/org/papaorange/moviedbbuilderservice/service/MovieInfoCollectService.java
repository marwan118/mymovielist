package org.papaorange.moviedbbuilderservice.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.papaorange.moviedbbuilderservice.model.ImdbImageItem;
import org.papaorange.moviedbbuilderservice.model.ImdbMediaViewerModelWrapperObject;
import org.papaorange.moviedbbuilderservice.model.LocalMovieInfo;
import org.papaorange.moviedbbuilderservice.model.MyMovieInfo;
import org.papaorange.moviedbbuilderservice.utils.AppConfigLoader;
import org.papaorange.moviedbbuilderservice.utils.DocumentHelper;
import org.papaorange.moviedbbuilderservice.utils.HttpGetUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class MovieInfoCollectService
{

    public static Map<String, LocalMovieInfo> getLocalMovieInfoAsMap()
    {

	Map<String, LocalMovieInfo> localMovieInfos = new HashMap<String, LocalMovieInfo>();
	String filePath = "";
	try
	{
	    filePath = AppConfigLoader.getProp("LocalMoviePath");
	}
	catch (IOException e)
	{
	    System.err.println("未找到配置文件./config/application.properties");
	    e.printStackTrace();
	}

	File movieDir = new File(filePath);

	for (String name : movieDir.list())
	{
	    // System.out.println(name);
	    if (new File(filePath + "/" + name).isDirectory() == false)
	    {
		continue;
	    }
	    LocalMovieInfo info = new LocalMovieInfo(filePath + "/" + name);
	    if (info.getMovieName().equals("unknown") || info.getYear().equals("unknown"))
	    {
		continue;
	    }
	    localMovieInfos.put(filePath + "/" + name, info);
	}
	return localMovieInfos;
    }

    public static MyMovieInfo getDoubanMovieInfoObjectCollectionByName(LocalMovieInfo info)
    {

	// 防止被BAN
	// Thread.sleep(1000);

	String doubanMovieInfo = "";
	try
	{
	    doubanMovieInfo = HttpGetUtil
		    .doGet(AppConfigLoader.getProp("MovieDBURL") + info.getMovieName().replace(" ", "%20"));
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
	List<MyMovieInfo> list = JSON.parseObject(doubanMovieInfo, new TypeReference<List<MyMovieInfo>>()
	{
	});

	int mvIdx = 0;
	int[] matchIdxArray = new int[list.size()];
	boolean fullMatch = false;
	for (int i = 0; i < list.size(); i++)
	{
	    if (list.get(i).getAlias().toLowerCase().replace(":", "").replace(" ", "").trim()
		    .equals(info.getMovieName().toLowerCase().replace(" ", "").trim()))
	    {
		// 记录match的次数与顺序，如果没有全命中，选取第一个matchIdx作为优选结果
		matchIdxArray[i] = 1;
		if ((list.get(i).getYear().trim().equals(info.getYear().trim())))
		{
		    // 如果名称和年代都相同，条件全命中，跳出循环
		    mvIdx = i;
		    fullMatch = true;
		    break;
		}
	    }
	}

	// 未发生全命中，选取第一个命中的index
	if (fullMatch == false)
	{
	    for (int i = 0; i < list.size(); i++)
	    {
		if (matchIdxArray[i] == 1)
		{
		    mvIdx = i;
		}
	    }
	}

	if (list.size() == 0)
	{
	    return null;
	}
	else
	{
	    MyMovieInfo temp = list.get(mvIdx);
	    temp.setLocalMvFileName(info.getMoviePath());
	    temp.setPlayableFilePath(info.getPlayerAblePath());
	    temp.setHasSubTitle(info.hasSubtitle());
	    return temp;
	}
    }

    private static String downloadImgFromIMDB(Document doc)
    {
	String imgUrl = null;
	String imdbMovieUrl = null;
	String filename = null;
	try
	{
	    String imdbTitleUrlString = "http://www.imdb.com/title";
	    String imdbUrl = "http://www.imdb.com";
	    imdbMovieUrl = doc.getElementsByAttributeValueContaining("href", imdbTitleUrlString).get(0).attr("href");

	    Document imdbDoc = DocumentHelper.getDocumentByUrl(imdbMovieUrl);

	    imdbDoc = DocumentHelper
		    .getDocumentByUrl(imdbUrl + imdbDoc.getElementsByClass("poster").get(0).child(0).attr("href"));

	    String imgJsonString = imdbDoc.getElementById("imageJson").html();/* 取imdb页面中json */

	    ImdbMediaViewerModelWrapperObject wrapperObject = JSON.parseObject(imgJsonString,
		    ImdbMediaViewerModelWrapperObject.class);
	    List<ImdbImageItem> images = wrapperObject.getModel().getAllImages();
	    for (ImdbImageItem item : images)
	    {
		if (item.getId().equals(wrapperObject.getStartingConst()))
		{
		    imgUrl = item.getSrc();
		    break;
		}
	    }
	    filename = imdbMovieUrl.substring(imdbMovieUrl.lastIndexOf("/") + 1) + ".jpg";
	    ResDownloader.download(imgUrl, "./db/" + filename);
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    return null;
	}
	return imgUrl + "#@#@" + filename;
    }

    public static String downloadImgFromDouban(String coverUrl)
    {
	String imgUrl = null;
	String filename = null;

	try
	{
	    String tempUrl = null;
	    Document tmpDoc = null;
	    try
	    {
		tmpDoc = Jsoup.parse(new URL(coverUrl), 30000);
	    }
	    catch (IOException e)
	    {
		e.printStackTrace();
	    }

	    try
	    {
		int i = 0;
		do
		{
		    tempUrl = tmpDoc.getElementsByClass("cover").get(i++).getElementsByTag("a").attr("href");
		    tmpDoc = Jsoup.parse(new URL(tempUrl), 30000);
		} while (tmpDoc.baseUri().equals("https://movie.douban.com/")
			|| i < tmpDoc.getElementsByClass("cover").size());

	    }
	    catch (IOException e)
	    {
		e.printStackTrace();
	    }

	    imgUrl = tmpDoc.getElementsByClass("mainphoto").get(0).getElementsByTag("img").get(0).attr("src");
	    filename = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
	    ResDownloader.download(imgUrl, "./db/" + filename);
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    return null;
	}
	return imgUrl + "#@#@" + filename;
    }

    public static void updateDetailInfo(MyMovieInfo mvInfo) throws Exception
    {
	Document doc = null;

	doc = DocumentHelper.getDocumentByUrl(mvInfo.getRefUrl());

	Elements summaryElem = doc.getElementsByAttributeValue("property", "v:summary");

	Elements yearElem = doc.getElementsByClass("year");

	Elements ratingElem = doc.getElementsByAttributeValue("property", "v:average");

	String coverUrl = doc.getElementsByClass("nbgnbg").get(0).attr("href");

	// // // 获取imdb海报
	//
	// String imgUrl = downloadImgFromIMDB(doc);
	//
	// if (imgUrl == null)
	// {
	// imgUrl = downloadImgFromDouban(coverUrl);
	// }
	//
	// if (imgUrl == null)
	// {
	// imgUrl = "";
	// }
	// mvInfo.setImgUrl(imgUrl.split("#@#@")[0]);
	// mvInfo.setLocalImgFileName(imgUrl.split("#@#@")[1]);
	mvInfo.setRatingValue(Double.parseDouble(ratingElem.get(0).text()));
	mvInfo.setSummaryText(summaryElem.get(0).text().trim());
	// mvInfo.setYear(yearElem.get(0).text().replace("(", "").replace(")",
	// ""));

    }
}
