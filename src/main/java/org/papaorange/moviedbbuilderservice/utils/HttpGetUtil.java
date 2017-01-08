package org.papaorange.moviedbbuilderservice.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpGetUtil
{

    /**
     * Get Request
     * 
     * @return
     * @throws Exception
     */
    public static String doGet(String url) throws Exception
    {

	URL localURL = new URL(url);

	URLConnection connection = localURL.openConnection();
	HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

	httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
	httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	httpURLConnection.setRequestProperty("User-agent",
		"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
	InputStream inputStream = null;
	InputStreamReader inputStreamReader = null;
	BufferedReader reader = null;
	StringBuffer resultBuffer = new StringBuffer();
	String tempLine = null;

	if (httpURLConnection.getResponseCode() >= 300)
	{
	    throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
	}

	try
	{
	    inputStream = httpURLConnection.getInputStream();
	    inputStreamReader = new InputStreamReader(inputStream);
	    reader = new BufferedReader(inputStreamReader);

	    while ((tempLine = reader.readLine()) != null)
	    {
		resultBuffer.append(tempLine);
	    }

	}
	finally
	{

	    if (reader != null)
	    {
		reader.close();
	    }

	    if (inputStreamReader != null)
	    {
		inputStreamReader.close();
	    }

	    if (inputStream != null)
	    {
		inputStream.close();
	    }

	}

	return resultBuffer.toString();
    }

}
