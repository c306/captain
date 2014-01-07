package com.c306.core.fileupload.entity;

import org.apache.commons.lang.RandomStringUtils;

import com.c306.core.fileupload.utils.img.ImageUtil;

public class Attachment extends com.c306.core.base.entity.BaseHbmEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String oid;
	private String objid;
	private String nodepath;
	private String moduleid;
	private String uri;
	private String title;
	private String desc;
	private String ouri;

	private int isvisible;
	private int width;
	private int heigh;
	
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getObjid() {
		return objid;
	}
	public void setObjid(String objid) {
		this.objid = objid;
	}
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOuri() {
		return ouri;
	}
	public void setOuri(String ouri) {
		this.ouri = ouri;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeigh() {
		return heigh;
	}
	public void setHeigh(int heigh) {
		this.heigh = heigh;
	}
	public int getIsvisible() {
		return isvisible;
	}
	public void setIsvisible(int isvisible) {
		this.isvisible = isvisible;
	}
	public String getPreviewImg() {
    	String imgType = ImageUtil.getImageTypeByFileName(this.getUri());
    	if(imgType != null)
    		return "zoom/" + this.getUri();
    	else {
			int pos = this.getUri().lastIndexOf('.');
			String suffix = (pos < 0) ? "" : this.getUri().substring(pos+1).toLowerCase();
			if(suffix.equalsIgnoreCase("FLV"))
		    	return "/images/common/video.jpg";
			if(suffix.equalsIgnoreCase("MP3"))
				return "/images/common/video.jpg";
			if(suffix.equals(""))
		    	return "/images/common/emptypic.jpg";
    	}
    	return "/images/common/emptypic.jpg";
    }
	public String getTmpid() {
		if(this.oid != null)
			return this.oid;
		return RandomStringUtils.random(12, 0, 0, true, false);
	}
	public String getNodepath() {
		return nodepath;
	}
	public void setNodepath(String nodepath) {
		this.nodepath = nodepath;
	}

}
