package com.c306.hbm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.c306.core.fileupload.entity.Attachment;

/**
 * TArticle entity. @author MyEclipse Persistence Tools
 */

public class TArticle extends com.c306.core.base.entity.BaseHbmEntity implements
		java.io.Serializable {

	// Fields

	private String oid;
	private String title;
	private String etitle;
	private String content;
	private String econtent;
	private String price;
	private String author;
	private String address;
	private String eaddress;
	private String objid;
	private String type;
	private String typePath;
	private String colorurl;
	private String blackurl;
	private String emap;

	//首页图片
	private List<Attachment> atts = new ArrayList<Attachment>(0);
	//黑白图片
	private List<Attachment> darkPhotos = new ArrayList<Attachment>(0);
	//彩色图片
	private List<Attachment> colorPhotos = new ArrayList<Attachment>(0);
	//大图片
	private List<Attachment> largePhotos = new ArrayList<Attachment>(0);
	

	// Constructors

	/** default constructor */
	public TArticle() {
	}

	/** minimal constructor */
	public TArticle(String oid) {
		this.oid = oid;
	}

	/** full constructor */
	public TArticle(String oid, String title, String etitle, String content,
			String econtent, String price, String author, String address,
			String eaddress, String objid, String type, String colorurl,
			String blackurl, Date createtime, Date updatetime, Integer ordinal,
			Integer status, String emap) {
		this.oid = oid;
		this.title = title;
		this.etitle = etitle;
		this.content = content;
		this.econtent = econtent;
		this.price = price;
		this.author = author;
		this.address = address;
		this.eaddress = eaddress;
		this.objid = objid;
		this.type = type;
		this.colorurl = colorurl;
		this.blackurl = blackurl;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.ordinal = ordinal;
		this.status = status;
		this.emap = emap;
	}

	// Property accessors

	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEtitle() {
		return this.etitle;
	}

	public void setEtitle(String etitle) {
		this.etitle = etitle;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEcontent() {
		return this.econtent;
	}

	public void setEcontent(String econtent) {
		this.econtent = econtent;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEaddress() {
		return this.eaddress;
	}

	public void setEaddress(String eaddress) {
		this.eaddress = eaddress;
	}

	public String getObjid() {
		return this.objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColorurl() {
		return this.colorurl;
	}

	public void setColorurl(String colorurl) {
		this.colorurl = colorurl;
	}

	public String getBlackurl() {
		return this.blackurl;
	}

	public void setBlackurl(String blackurl) {
		this.blackurl = blackurl;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getOrdinal() {
		return this.ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Attachment> getAtts() {
		return atts;
	}

	public void setAtts(List<Attachment> atts) {
		this.atts = atts;
	}

	public List<Attachment> getDarkPhotos() {
		return darkPhotos;
	}

	public void setDarkPhotos(List<Attachment> darkPhotos) {
		this.darkPhotos = darkPhotos;
	}

	public List<Attachment> getLargePhotos() {
		return largePhotos;
	}

	public void setLargePhotos(List<Attachment> largePhotos) {
		this.largePhotos = largePhotos;
	}

	public List<Attachment> getColorPhotos() {
		return colorPhotos;
	}

	public void setColorPhotos(List<Attachment> colorPhotos) {
		this.colorPhotos = colorPhotos;
	}

	public String getTypePath() {
		return typePath;
	}

	public void setTypePath(String typePath) {
		this.typePath = typePath;
	}

	public String getEmap() {
		return emap;
	}

	public void setEmap(String emap) {
		this.emap = emap;
	}

}