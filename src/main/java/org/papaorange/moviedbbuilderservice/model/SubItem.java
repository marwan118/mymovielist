package org.papaorange.moviedbbuilderservice.model;

import org.papaorange.moviedbbuilderservice.model.SubFile;

import com.alibaba.fastjson.annotation.JSONField;

public class SubItem
{

    private String desc;
    private int delay;
    private SubFile[] files;

    @JSONField(name = "Desc")
    public String getDesc()
    {
	return desc;
    }

    @JSONField(name = "Desc")
    public void setDesc(String desc)
    {
	this.desc = desc;
    }

    @JSONField(name = "Delay")
    public int getDelay()
    {
	return delay;
    }

    @JSONField(name = "Delay")
    public void setDelay(int delay)
    {
	this.delay = delay;
    }

    @JSONField(name = "Files")
    public SubFile[] getFiles()
    {
	return files;
    }
    
    @JSONField(name = "Files")
    public void setFiles(SubFile[] files)
    {
	this.files = files;
    }

}
