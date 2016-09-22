package org.papaorange.mymovielist.model;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class ImdbMediaViewerModel {

	private List<ImdbImageItem> allImages;


	@JSONField(name = "allImages")
	public List<ImdbImageItem> getAllImages() {
		return allImages;
	}

	@JSONField(name = "allImages")
	public void setAllImages(List<ImdbImageItem> allImages) {
		this.allImages = allImages;
	}
}
