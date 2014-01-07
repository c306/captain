/*
 * $Id: MultipleFileUploadUsingListAction.java 660522 2008-05-27 14:08:00Z jholmes $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
// START SNIPPET: entire-file
package com.c306.core.fileupload;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.codehaus.jackson.map.ObjectMapper;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.springframework.beans.factory.annotation.Autowired;

import com.c306.core.CoreConstants;
import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.core.fileupload.entity.Attachment;
import com.c306.core.fileupload.service.AttachmentManager;
import com.c306.core.fileupload.utils.img.ImageUtil;

/**
 * Showcase action - multiple file upload using List
 */
public class FileUploadAction extends BaseGenericActionSupport<Attachment>// implements ServletContextAware 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Log LOG = LogFactory.getLog(FileUploadAction.class);
	
//	public final static String PROJECTREALPATH= ServletActionContext.getServletContext().getRealPath("/");
//	public final static String UPLOADPATH = "upload/temp";
	
	private final static ObjectMapper objectMapper = new ObjectMapper();
	
//	private ServletContext servletContext;

	private File upload;
    private String uploadContentType;
    private String uploadFileName;
    
    private String destDir = "";
    private String destFileName = "";
    
    private String uploadparams = "";
    
    //图片宽、高
    private int width;
    private int height;
    //不等于0，表示需要缩放
    private int zoomW;
    private int zoomH;
    
    @Autowired
    private AttachmentManager service;
    
    private void initParams(){
    	if(StringUtils.isBlank(destDir)){
    		destDir = CoreConstants.UPLOAD_STORE_PATH + String.format("/%1$tY-%1$tm-%1$td/", new java.util.Date());
    	}
    	if(StringUtils.isBlank(destFileName)){
    		destFileName = destDir + String.format("%1$tM%1$ts%1$tL", new java.util.Date());
    		int pos = this.uploadFileName.lastIndexOf('.');
    		destFileName += (pos < 0) ? "" : this.uploadFileName.substring(pos).toUpperCase();
    	} else {
        	destFileName = destDir.concat(destFileName);
    	}
    }

    public String upload() {
    	
    	LOG.debug("\n\n upload1");
    	
        try {
        	
        	initParams();
        	
			FileUtils.copyFile(upload, new File(CoreConstants.UPLOAD_STORE_PATH.concat(destFileName)), true);
			
		} catch (IOException e) {
			LOG.error("上传失败", e);
		}
        
        LOG.debug("destFileName:" + this.destFileName);
        LOG.debug("filename:" + this.uploadFileName);
        LOG.debug("content type:" + uploadContentType);
        
        return SUCCESS;
    }
    
    public String delete(){
    	
    	try {
			initParams();
			
			File currentFile = new File(CoreConstants.UPLOAD_STORE_PATH.concat(destFileName));
			if ((currentFile != null) && currentFile.isFile()) {
			    if (LOG.isInfoEnabled()) {
			        LOG.info(this.getText("struts.messages.removing.file", new String[]{destFileName, currentFile.getCanonicalPath()}));
			    }
			    if (currentFile.delete() == false) {
					LOG.warn("Resource Leaking:  Could not remove uploaded file '" + currentFile.getCanonicalPath() + "'.");
			    }
			}
		} catch (IOException e) {
			LOG.error("", e);
		}
    	
    	return SUCCESS;
    }
    
    public String img(){
    	this.upload();
		String imgType = ImageUtil.getImageTypeByFileName(destFileName);
    	//生成小图
		if(zoomW <= 0) {
			this.zoomW = 96;
			this.zoomH = 72;
		}
		if(imgType != null && zoomW>0 && zoomH>0){
			String src = CoreConstants.UPLOAD_STORE_PATH.concat(destFileName);
			String zoomName = CoreConstants.UPLOAD_STORE_PATH.concat("zoom/" + destFileName);
			try {
				File f = new File(zoomName);
				if(!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				ImageUtil.changeSize(imgType, new File(src), f, zoomW, zoomH);
			} catch (Exception e) {
				LOG.error(e);
			}
		}
//		return rs;
		return "uploadsuccess";
    }
    
    /**
     * 利用im4java调用ImageMagick命令生成图片
     * @return
     */
    public String im4j(){
    	this.upload();
    	String imgType = ImageUtil.getImageTypeByFileName(destFileName);
		String src = CoreConstants.UPLOAD_STORE_PATH.concat(destFileName);
		
		LOG.debug("ProcessStarter.getGlobalSearchPath()="+ProcessStarter.getGlobalSearchPath());
		// init im4j
		if(ProcessStarter.getGlobalSearchPath() == null){
			ProcessStarter.setGlobalSearchPath(CoreConstants.IM4JAVA_TOOLPATH);
		}

    	if(imgType != null && width>0 && height>0){
    		ConvertCmd convert = new ConvertCmd();
    		try {
	    		IMOperation op = new IMOperation();
	    		op.addImage();
	    		op.resize(width, height);
	    		op.addImage();
	    		
				convert.run(op, new Object[]{src, src});
			} catch (IOException e) {
    			LOG.error("im4java 处理图片IO异常。", e);
			} catch (InterruptedException e) {
    			LOG.error("im4java 处理图片Interrupted异常。", e);
			} catch (IM4JavaException e) {
    			LOG.error("im4java 处理图片IM4Java异常。", e);
			}
    	}
    	//生成小图
    	if(imgType != null && zoomW>0 && zoomH>0){
    		
    		ConvertCmd convert = new ConvertCmd();
    		try {
    			String zoomName = CoreConstants.UPLOAD_STORE_PATH.concat("zoom/" + destFileName);
//    			zoomName = new String(zoomName.getBytes(), "GBK");
//    			String src1 = new String(src.getBytes(), "GBK");
    			
//    			zoomName = new String(zoomName.getBytes(), "GBK");
//    			src1 = new String(src1.getBytes(), "GBK");
//    			convert.createScript("resize.sh",op);
    			
    			File f = new File(zoomName);
				if(!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				
				IMOperation op = new IMOperation();
	    		op.addImage(src);
	    		op.resize(zoomW, zoomH);
	    		op.addImage(zoomName);
	    		
				//convert.run(op, "F:\\temp\\tt\\项目\\1234.jpg", f.getAbsolutePath());
				convert.run(op);
			} catch (IOException e) {
    			LOG.error("im4java 处理图片IO异常。", e);
			} catch (InterruptedException e) {
    			LOG.error("im4java 处理图片Interrupted异常。", e);
			} catch (IM4JavaException e) {
    			LOG.error("im4java 处理图片IM4Java异常。", e);
			}
    	}
//    	return rs;
    	return "uploadsuccess";
    }
    
    
    /**
     * AnyWearStyle网站专用，生成三套图片
     * @return
     */
    public String anywear(){

    	initParams();
    	
    	String imgType = ImageUtil.getImageTypeByFileName(destFileName);
		String src = CoreConstants.UPLOAD_STORE_PATH.concat(destFileName);

		int pos = src.lastIndexOf(".");
		String org = src.substring(0, pos) + "_o" + src.substring(pos);//原图地址
		
        try {
			FileUtils.copyFile(upload, new File(org), true);
		} catch (IOException e) {
			LOG.error("上传失败", e);
		}
		
		// init im4j
		if(ProcessStarter.getGlobalSearchPath() == null){
			ProcessStarter.setGlobalSearchPath(CoreConstants.IM4JAVA_TOOLPATH);
		}
		LOG.debug("ProcessStarter.getGlobalSearchPath()="+ProcessStarter.getGlobalSearchPath());

    	if(imgType != null && width>0 && height>0){
    		ConvertCmd convert = new ConvertCmd();
    		try {
	    		IMOperation op = new IMOperation();
	    		op.addImage();
	    		op.resize(width, height);
	    		op.addImage();
	    		
				convert.run(op, new Object[]{org, src});
			} catch (IOException e) {
    			LOG.error("im4java 处理图片IO异常。", e);
			} catch (InterruptedException e) {
    			LOG.error("im4java 处理图片Interrupted异常。", e);
			} catch (IM4JavaException e) {
    			LOG.error("im4java 处理图片IM4Java异常。", e);
			}
    	}
    	
    	//生成小图cover
    	if(imgType != null && zoomW>0 && zoomH>0){
    		
    		ConvertCmd convert = new ConvertCmd();
    		try {
    			String zoomName = CoreConstants.UPLOAD_STORE_PATH.concat("zoom/" + destFileName);
    			
    			File f = new File(zoomName);
				if(!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				
				IMOperation op = new IMOperation();
	    		op.addImage(org);
	    		op.resize(zoomW, zoomH);
	    		op.addImage(zoomName);
	    		
				convert.run(op);
				
				//详细页面图片
				zoomName = CoreConstants.UPLOAD_STORE_PATH.concat("medium/" + destFileName);
    			
    			f = new File(zoomName);
				if(!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				
				op = new IMOperation();
	    		op.addImage(org);
	    		op.resize(484, 326);
	    		op.addImage(zoomName);
	    		
				convert.run(op);
				
			} catch (IOException e) {
    			LOG.error("im4java 处理图片IO异常。", e);
			} catch (InterruptedException e) {
    			LOG.error("im4java 处理图片Interrupted异常。", e);
			} catch (IM4JavaException e) {
    			LOG.error("im4java 处理图片IM4Java异常。", e);
			}
    	}
    	return SUCCESS;
    }
    

    @SkipValidation
    public String anywearflashupload(){
    	this.width = 960;
    	this.height = 460;
    	this.zoomW = 187;
    	this.zoomH = 170;
    	anywear();
    	return "uploadsuccess";
    }

    /**
     * anyshopstyle生成图片
     * @return
     */
    @SkipValidation
    public String anyshopstyleflashupload(){
    	int mediumW = 226;
    	int mediumH = 302;
    	if(domain != null && "9".equals(domain.getOid())) {
    		this.width = 720;
        	this.height = 440;
        	this.zoomW = 140;
        	this.zoomH = 86;
    	} else if(domain != null && "10".equals(domain.getOid())) {
    		this.width = 370;
        	this.height = 495;
        	this.zoomW = 81;
        	this.zoomH = 180;
        	mediumW = 307;
        	mediumH = 409;
    	} else {
    		this.width = 370;
        	this.height = 495;
        	this.zoomW = 81;
        	this.zoomH = 180;
    	}
    	

    	initParams();
    	
    	String imgType = ImageUtil.getImageTypeByFileName(destFileName);
		String src = CoreConstants.UPLOAD_STORE_PATH.concat(destFileName);

		int pos = src.lastIndexOf(".");
		String org = src.substring(0, pos) + "_o" + src.substring(pos);//原图地址
		
        try {
			FileUtils.copyFile(upload, new File(org), true);
		} catch (IOException e) {
			LOG.error("上传失败", e);
		}
		
		// init im4j
		if(ProcessStarter.getGlobalSearchPath() == null){
			ProcessStarter.setGlobalSearchPath(CoreConstants.IM4JAVA_TOOLPATH);
		}
		LOG.debug("ProcessStarter.getGlobalSearchPath()="+ProcessStarter.getGlobalSearchPath());

    	if(imgType != null && width>0 && height>0){
    		ConvertCmd convert = new ConvertCmd();
    		try {
	    		IMOperation op = new IMOperation();
	    		op.addImage();
	    		op.resize(width, height);
	    		op.addImage();
	    		
				convert.run(op, new Object[]{org, src});
			} catch (IOException e) {
    			LOG.error("im4java 处理图片IO异常。", e);
			} catch (InterruptedException e) {
    			LOG.error("im4java 处理图片Interrupted异常。", e);
			} catch (IM4JavaException e) {
    			LOG.error("im4java 处理图片IM4Java异常。", e);
			}
    	}
    	
    	//生成小图cover
    	if(imgType != null && zoomW>0 && zoomH>0){
    		
    		ConvertCmd convert = new ConvertCmd();
    		try {
    			String zoomName = CoreConstants.UPLOAD_STORE_PATH.concat("zoom/" + destFileName);
    			
    			File f = new File(zoomName);
				if(!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				
				IMOperation op = new IMOperation();
	    		op.addImage(org);
	    		op.resize(zoomW, zoomH);
	    		op.addImage(zoomName);
	    		
				convert.run(op);
				
				//详细页面图片
				zoomName = CoreConstants.UPLOAD_STORE_PATH.concat("medium/" + destFileName);
    			
    			f = new File(zoomName);
				if(!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				
				op = new IMOperation();
	    		op.addImage(org);
	    		op.resize(mediumW, mediumH);
	    		op.addImage(zoomName);
	    		
				convert.run(op);
				
			} catch (IOException e) {
    			LOG.error("im4java 处理图片IO异常。", e);
			} catch (InterruptedException e) {
    			LOG.error("im4java 处理图片Interrupted异常。", e);
			} catch (IM4JavaException e) {
    			LOG.error("im4java 处理图片IM4Java异常。", e);
			}
    	}
    	
    	return "uploadsuccess";
    }
    
    public String list() {
    	if(domain.getObjid() != null) {
//    		this.items = this.getService().findBy("objid", this.domain.getObjid(), "ordinal", true);
    		try {
				this.items = this.getService().list(domain);
			} catch (Exception e) {
				logger.error("", e);
			}
    	}
    	if(!GenericValidator.isBlankOrNull(getDefaultResult()))
    		return getDefaultResult();
    	return LIST_RESULT;
    }
    
    public String getPreviewImg(){
    	String imgType = ImageUtil.getImageTypeByFileName(destFileName);
    	if(imgType != null && zoomW > 0)
    		return "/zoom/" + this.destFileName;
    	else if(imgType != null) {
    		return this.destFileName;
    	} else {
			int pos = this.uploadFileName.lastIndexOf('.');
			String suffix = (pos < 0) ? "" : this.uploadFileName.substring(pos+1).toLowerCase();
			if(suffix.equalsIgnoreCase("FLV"))
		    	return "/images/common/video.jpg";
			if(suffix.equalsIgnoreCase("MP3"))
				return "/images/common/video.jpg";
			if(suffix.equals(""))
		    	return "/images/common/emptypic.jpg";
    	}
    	return "/images/common/emptypic.jpg";
    }

	public Attachment getUploadSuccessItem() {
		if(this.domain == null)
			this.domain = new Attachment();
		
//		domain.setTmpid();//temp id
		domain.setIsvisible(1);
//		domain.setModuleid("");
//		domain.setObjid(objid);
//		domain.setPreviewImg(this.getPreviewImg());
		domain.setUri(this.getDestFileName());
		domain.setTitle(this.getUploadFileName());
		
		return this.domain;
	}
	
	public String getJsonItems() {
		try {
			if(this.items != null)
			return objectMapper.writeValueAsString(this.items);
		} catch (Exception e) {
			logger.error("get upload json object err.", e);
		}
		return "[]";
	}
	
	public String getDestDir() {
		return destDir;
	}

	public void setDestDir(String destDir) {
		this.destDir = destDir;
	}

	public String getDestFileName() {
		return destFileName;
	}

	public void setDestFileName(String destFileName) {
		this.destFileName = destFileName;
	}

	public int getZoomW() {
		return zoomW;
	}

	public void setZoomW(int zoomW) {
		this.zoomW = zoomW;
	}

	public int getZoomH() {
		return zoomH;
	}

	public void setZoomH(int zoomH) {
		this.zoomH = zoomH;
	}

	@Override
	protected AttachmentManager getService() {
		return this.service;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getUploadparams() {
		return uploadparams;
	}

	public void setUploadparams(String uploadparams) {
		this.uploadparams = uploadparams;
	}

}
