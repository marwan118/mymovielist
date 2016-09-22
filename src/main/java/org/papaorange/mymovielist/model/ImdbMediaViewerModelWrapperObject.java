package org.papaorange.mymovielist.model;

import com.alibaba.fastjson.annotation.JSONField;

public class ImdbMediaViewerModelWrapperObject {
	private ImdbMediaViewerModel model;
	private String startingConst;
	
	
	@JSONField(name = "mediaViewerModel")
	public ImdbMediaViewerModel getModel() {
		return model;
	}
	
	@JSONField(name = "mediaViewerModel")
	public void setModel(ImdbMediaViewerModel model) {
		this.model = model;
	}
	
	@JSONField(name = "startingConst")
	public String getStartingConst() {
		return startingConst;
	}
	
	@JSONField(name = "startingConst")
	public void setStartingConst(String startingConst) {
		this.startingConst = startingConst;
	}
	
	
	
}
