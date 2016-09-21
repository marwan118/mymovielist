package org.papaorange.mymovielist.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.util.FileCopyUtils;

public class ImgDownloader {
	public static String download(String url) throws Exception {

		URL localURL = new URL(url);
		FileOutputStream fileOutputStream = null;
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;

		StringBuffer resultBuffer = new StringBuffer();

		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}

		try {
			inputStream = httpURLConnection.getInputStream();
			String filename = null;
			if (httpURLConnection.getContentType().equals("image/jpeg")) {
				filename = url.substring(url.lastIndexOf("/")+1);
			}
			fileOutputStream = new FileOutputStream(new File(filename));

			FileCopyUtils.copy(inputStream, fileOutputStream);

		} finally {

			if (fileOutputStream != null) {
				fileOutputStream.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}

		return resultBuffer.toString();
	}

	public static void main(String[] args) {
		try {
			download("https://img3.doubanio.com/view/photo/photo/public/p2360924286.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
