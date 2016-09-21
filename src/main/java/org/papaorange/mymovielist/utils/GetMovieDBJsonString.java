package org.papaorange.mymovielist.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetMovieDBJsonString {
	public static String getJsonString() throws IOException {
		String jsonString = "";
		BufferedReader reader = null;
		FileInputStream in = new FileInputStream("movieDB.json");

		try {
			InputStreamReader isr = new InputStreamReader(in, "UTF-8");
			reader = new BufferedReader(isr);
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString += line;
			}

		} finally {
			reader.close();
		}
		return jsonString;
	}
}
