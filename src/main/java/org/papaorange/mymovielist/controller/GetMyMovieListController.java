package org.papaorange.mymovielist.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
public class GetMyMovieListController {

	@RequestMapping("/mymovies")
	public Object getMyMovieList() throws IOException {

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

		return JSON.parse(jsonString);
	}

}
