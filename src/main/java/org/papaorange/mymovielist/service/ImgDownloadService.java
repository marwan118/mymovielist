package org.papaorange.mymovielist.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class ImgDownloadService {

	private static final Logger log = LoggerFactory.getLogger(UpdateMovieDBTaskSchedulerService.class);

	public static String download(String url, String filename) throws Exception {

		URL localURL = new URL(url);
		FileOutputStream fileOutputStream = null;
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;

		StringBuffer resultBuffer = new StringBuffer();

		log.info("\033[32mGET:" + url);

		if (httpURLConnection.getResponseCode() >= 300) {
			log.warn("GET:" + url + "\n" + "HTTP Request is not success, Response code is "
					+ httpURLConnection.getResponseCode());

			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}

		try {
			inputStream = httpURLConnection.getInputStream();

			fileOutputStream = new FileOutputStream(new File("./db/" + filename));

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
}
