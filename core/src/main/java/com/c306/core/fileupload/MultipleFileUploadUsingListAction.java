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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.ServletContextAware;

import com.c306.core.CoreConstants;
import com.c306.core.fileupload.utils.img.ImageUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Showcase action - multiple file upload using List
 * @version $Date: 2008-05-27 16:08:00 +0200 (Tue, 27 May 2008) $ $Id: MultipleFileUploadUsingListAction.java 660522 2008-05-27 14:08:00Z jholmes $
 */
public class MultipleFileUploadUsingListAction extends ActionSupport implements ServletContextAware {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Log LOG = LogFactory.getLog(MultipleFileUploadUsingListAction.class);
	
	private ServletContext servletContext;

    private List<File> uploads = new ArrayList<File>();
    private List<String> uploadFileNames = new ArrayList<String>();
    private List<String> uploadContentTypes = new ArrayList<String>();
    
    private String destDir = "";
    private String destFileName = "";
    
    //不等于0，表示需要缩放
    private int zoomW;
    private int zoomH;

    public String upload() throws Exception {
    	
    	LOG.debug("\n\n upload1");
    	LOG.debug("files:");
    	int i=0;
        for (File u: uploads) {
            try {
            	if(StringUtils.isBlank(destDir)){
            		destDir = CoreConstants.UPLOAD_STORE_PATH;
            		destDir += String.format("/%1$tY-%1$tm-%1$td/", new java.util.Date());
            	}
            	if(StringUtils.isBlank(destFileName)){
            		destFileName = String.format("%1$tM%1$ts%1$tL", new java.util.Date());
                	destFileName = destDir.concat(destFileName)+uploadFileNames.get(i);
            	} else {
                	destFileName = destDir.concat(destFileName);
            	}
            	uploadFileNames.set(i, destFileName);
            	i++;
            	String src = servletContext.getRealPath("/").concat(destFileName);
				FileUtils.copyFile(u, new File(src), true);
				
				//生成小图
				if(zoomW>0 && zoomH>0){
					String imgType = ImageUtil.getImageTypeByFileName(src);
					String zoomName = servletContext.getRealPath("/").concat("zoom/" + destFileName);
					try {
						File f = new File(zoomName);
						if(!f.getParentFile().exists())
							f.mkdirs();
						ImageUtil.changeSize(imgType, new File(src), f, zoomW, zoomH);
					} catch (Exception e) {
						LOG.error(e);
					}
				}
			} catch (IOException e) {
				LOG.error("上传失败", e);
			}
            LOG.debug("*** " + u + "\t" + u.length());
        }
        LOG.debug("filenames:");
        for (String n: uploadFileNames) {
        	LOG.debug("*** " + n);
        }
        LOG.debug("content types:");
        for (String c: uploadContentTypes) {
        	LOG.debug("*** " + c);
        }
        LOG.debug("\n\n");
        return SUCCESS;
    }
    
    public List<File> getUpload() {
        return this.uploads;
    }
    public void setUpload(List<File> uploads) {
        this.uploads = uploads;
    }

    public List<String> getUploadFileName() {
        return this.uploadFileNames;
    }
    public void setUploadFileName(List<String> uploadFileNames) {
        this.uploadFileNames = uploadFileNames;
    }
    public List<String> getUploadContentType() {
        return this.uploadContentTypes;
    }
    public void setUploadContentType(List<String> contentTypes) {
        this.uploadContentTypes = contentTypes;
    }
	public String getDestDir() {
		return destDir;
	}
	public void setDestDir(String destDir) {
		this.destDir = destDir;
	}
	
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
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
}
// END SNIPPET: entire-file