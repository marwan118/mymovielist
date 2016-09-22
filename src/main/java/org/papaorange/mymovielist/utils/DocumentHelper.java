package org.papaorange.mymovielist.utils;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DocumentHelper {
	public static Document getDocumentByUrl(String url) {
		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(url), 30000);
			// System.err.println(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;
	}
}
