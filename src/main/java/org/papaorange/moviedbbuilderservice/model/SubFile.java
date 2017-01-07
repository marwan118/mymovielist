package org.papaorange.moviedbbuilderservice.model;

import com.alibaba.fastjson.annotation.JSONField;

public class SubFile
{
    private String subExt;
    private String link;

    @JSONField(name = "Ext")
    public String getSubExt()
    {
	return subExt;
    }

    @JSONField(name = "Ext")
    public void setSubExt(String subExt)
    {
	this.subExt = subExt;
    }

    @JSONField(name = "Link")
    public String getLink()
    {
	return link;
    }

    @JSONField(name = "Link")
    public void setLink(String link)
    {
	this.link = link;
    }

}
