package org.papaorange.moviedbbuilderservice.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class AppConfigLoader
{

    private static Properties properties = new Properties();

    public static String getProp(String key) throws IOException
    {
	FileInputStream fileInputStream = new FileInputStream("./config/application.properties");
	properties.load(new InputStreamReader(fileInputStream, "utf-8"));
	fileInputStream.close();
	return properties.getProperty(key);
    }
}
