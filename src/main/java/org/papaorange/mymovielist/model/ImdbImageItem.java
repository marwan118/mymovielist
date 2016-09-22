package org.papaorange.mymovielist.model;

import com.alibaba.fastjson.annotation.JSONField;

public class ImdbImageItem {

	private String id;
	private String src;

	@JSONField(name = "id")
	public String getId() {
		return id;
	}

	@JSONField(name = "id")
	public void setId(String id) {
		this.id = id;
	}

	@JSONField(name = "src")
	public String getSrc() {
		return src;
	}

	@JSONField(name = "src")
	public void setSrc(String src) {
		this.src = src;
	}

}
