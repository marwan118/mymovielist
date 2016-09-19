package org.papaorange.mymovielist.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfigLoader {

	private static Properties properties = new Properties();

	public static String getProp(String key) throws IOException {
		FileInputStream fileInputStream = new FileInputStream("./config/application.properties");
		properties.load(fileInputStream);
		fileInputStream.close();
		return properties.getProperty(key);
	}
}
