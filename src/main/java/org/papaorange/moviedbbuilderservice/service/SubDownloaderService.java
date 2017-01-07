package org.papaorange.moviedbbuilderservice.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.List;

import org.apache.log4j.Logger;
import org.papaorange.moviedbbuilderservice.model.SubItem;

import com.alibaba.fastjson.JSON;

public class SubDownloaderService
{

    private static final Logger log = Logger.getLogger(SubDownloaderService.class);

    private static String calcVideoHash(String filepath) throws Exception
    {
	String ret = "";
	long offset[] = new long[4];
	RandomAccessFile rafVideo = new RandomAccessFile(filepath, "r");
	long fLength = rafVideo.length();

	if (rafVideo.length() < 8192)
	{
	    rafVideo.close();
	    return "ERROR_HASH";
	}
	else
	{
	    offset[3] = fLength - 8192;
	    offset[2] = fLength / 3;
	    offset[1] = fLength / 3 * 2;
	    offset[0] = 4096;

	    byte bBuf[] = new byte[4096];
	    for (int i = 0; i < 4; i++)
	    {
		rafVideo.seek(offset[i]);
		rafVideo.read(bBuf, 0, 4096);

		String md5 = getMD5(bBuf);
		if (!ret.isEmpty())
		{
		    ret += ";";
		}
		ret += md5;
	    }
	}
	rafVideo.close();
	return ret;
    }

    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    private static String getMD5(byte[] buff) throws Exception
    {
	try
	{
	    // 生成一个MD5加密计算摘要
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    // 计算md5函数
	    md.update(buff);
	    return new BigInteger(1, md.digest()).toString(16);
	}
	catch (Exception e)
	{
	    throw new Exception("MD5加密出现错误");
	}
    }

    private static List<SubItem> querySubInfo(String hash, String path, String format, String lang) throws Exception
    {
	URL localURL = null;
	String params = "";
	String url = "https://www.shooter.cn/api/subapi.php";
	params = "filehash=" + URLEncoder.encode(hash, "UTF-8") + "&pathinfo=" + URLEncoder.encode(path, "UTF-8")
		+ "&format=" + URLEncoder.encode(format, "UTF-8") + "&lang=" + URLEncoder.encode(lang, "UTF-8");
	localURL = new URL(url);

	URLConnection connection = null;
	try
	{
	    connection = localURL.openConnection();
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
	HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

	httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
	httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	httpURLConnection.setDoOutput(true);
	httpURLConnection.setDoInput(true);
	httpURLConnection.setUseCaches(false);

	httpURLConnection.setRequestMethod("POST");

	DataOutputStream dos = null;
	dos = new DataOutputStream(httpURLConnection.getOutputStream());
	dos.writeBytes(params);
	dos.flush();
	dos.close();

	int responseCode = httpURLConnection.getResponseCode();

	if (responseCode >= 300)
	{
	    log.warn("GET:" + url + "\n" + "HTTP Request is not success, Response code is " + responseCode);

	    throw new Exception("HTTP Request is not success, Response code is " + responseCode);
	}
	else
	{
	    BufferedReader bufferedReader = new BufferedReader(
		    new InputStreamReader(httpURLConnection.getInputStream()));
	    String jsonString = "", line;
	    while ((line = bufferedReader.readLine()) != null)
	    {
		jsonString = jsonString + line;
	    }
	    List<SubItem> items = JSON.parseArray(jsonString, SubItem.class);
	    return items;
	}
    }

    public static void downloadSub(String filePath)
    {
	String hash = null;

	try
	{
	    hash = calcVideoHash(filePath);
	}
	catch (Exception e1)
	{
	    log.info("计算文件hash失败...");
	    e1.printStackTrace();
	    return;
	}

	List<SubItem> items = null;

	try
	{
	    items = querySubInfo(hash, filePath, "json", "cn");
	}
	catch (Exception e)
	{
	    String mvName = filePath.substring(filePath.lastIndexOf("/") + 1);
	    log.error("电影[" + mvName + "]没有查询到相应字幕...");
	    // e.printStackTrace();
	    return;
	}

	if (items.isEmpty())
	{
	    log.info("没有查询到相应字幕...");
	    return;
	}
	for (SubItem subItem : items)
	{
	    String link = subItem.getFiles()[0].getLink();
	    String oldExt = filePath.substring(filePath.lastIndexOf(".") + 1);
	    String subPath = filePath.replace(oldExt, subItem.getFiles()[0].getSubExt());
	    System.out.println(oldExt);
	    System.out.println(link);
	    try
	    {
		ResDownloader.download(link, subPath);
	    }
	    catch (Exception e)
	    {
		e.printStackTrace();
	    }
	}
    }
}
