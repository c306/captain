/*     */ package com.ckfinder.connector.handlers.command;
/*     */ 
/*     */ import com.ckfinder.connector.configuration.IConfiguration;
/*     */ import com.ckfinder.connector.data.ResourceType;
/*     */ import com.ckfinder.connector.errors.ConnectorException;
/*     */ import com.ckfinder.connector.utils.AccessControlUtil;
/*     */ import com.ckfinder.connector.utils.FileUtils;
/*     */ import com.ckfinder.connector.utils.ImageUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class ThumbnailCommand extends Command
/*     */ {
/*     */   private String fileName;
/*     */   private File thumbFile;
/*     */   private String ifNoneMatch;
/*     */   private long ifModifiedSince;
/*     */   private HttpServletResponse response;
/*     */   private String fullCurrentPath;
/*     */ 
/*     */   public void setResponseHeader(HttpServletResponse response, ServletContext sc)
/*     */   {
/*  62 */     response.setHeader("Cache-Control", "public");
/*     */ 
/*  64 */     String mimetype = getMimeTypeOfImage(sc, response);
/*     */ 
/*  67 */     if (mimetype != null) {
/*  68 */       response.setContentType(mimetype.concat("; name=\"") + this.fileName + "\"");
/*     */     }
/*     */     else {
/*  71 */       response.setContentType("name=\"" + this.fileName + "\"");
/*     */     }
/*     */ 
/*  75 */     this.response = response;
/*     */   }
/*     */ 
/*     */   private String getMimeTypeOfImage(ServletContext sc, HttpServletResponse response)
/*     */   {
/*  90 */     if ((this.fileName == null) || (this.fileName.equals(""))) {
/*  91 */       response.setStatus(500);
/*  92 */       return null;
/*     */     }
/*  94 */     String tempFileName = this.fileName.substring(0, this.fileName.lastIndexOf(46) + 1).concat(FileUtils.getFileExtension(this.fileName).toLowerCase());
/*     */ 
/*  97 */     String mimeType = sc.getMimeType(tempFileName);
/*  98 */     if (mimeType == null) {
/*  99 */       response.setStatus(500);
/* 100 */       return null;
/*     */     }
/* 102 */     return mimeType;
/*     */   }
/*     */ 
/*     */   public void execute(OutputStream out) throws ConnectorException
/*     */   {
/* 107 */     validate();
/* 108 */     createThumb();
/* 109 */     if (setResponseHeadersAfterCreatingFile())
/*     */       try {
/* 111 */         FileUtils.printFileContentToResponse(this.thumbFile, out);
/*     */       } catch (IOException e) {
/* 113 */         if (this.configuration.isDebugMode())
/* 114 */           throw new ConnectorException(e);
/*     */         try
/*     */         {
/* 117 */           this.response.sendError(403);
/*     */         }
/*     */         catch (IOException e1) {
/* 120 */           throw new ConnectorException(e1);
/*     */         }
/*     */       }
/*     */     else
/*     */       try {
/* 125 */         this.response.reset();
/* 126 */         this.response.sendError(304);
/*     */       }
/*     */       catch (IOException e1) {
/* 129 */         throw new ConnectorException(e1);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void initParams(HttpServletRequest request, IConfiguration configuration, Object[] params)
/*     */     throws ConnectorException
/*     */   {
/* 138 */     super.initParams(request, configuration, params);
/* 139 */     this.fileName = getParameter(request, "FileName");
/*     */     try {
/* 141 */       this.ifModifiedSince = Long.valueOf(request.getDateHeader("If-Modified-Since")).longValue();
/*     */     }
/*     */     catch (IllegalArgumentException e) {
/* 144 */       this.ifModifiedSince = 0L;
/*     */     }
/* 146 */     this.ifNoneMatch = request.getHeader("If-None-Match");
/*     */   }
/*     */ 
/*     */   private void validate()
/*     */     throws ConnectorException
/*     */   {
/* 158 */     if (!(this.configuration.getThumbsEnabled())) {
/* 159 */       throw new ConnectorException(501);
/*     */     }
/*     */ 
/* 163 */     if (!(AccessControlUtil.getInstance(this.configuration).checkFolderACL(this.type, this.currentFolder, this.userRole, 16)))
/*     */     {
/* 166 */       throw new ConnectorException(103);
/*     */     }
/*     */ 
/* 170 */     if (!(FileUtils.checkFileName(this.fileName))) {
/* 171 */       throw new ConnectorException(109);
/*     */     }
/*     */ 
/* 175 */     if (FileUtils.checkIfFileIsHidden(this.fileName, this.configuration)) {
/* 176 */       throw new ConnectorException(117);
/*     */     }
/*     */ 
/* 180 */     File typeThumbDir = new File(this.configuration.getThumbsPath() + File.separator + this.type);
/*     */     try
/*     */     {
/* 184 */       this.fullCurrentPath = typeThumbDir.getAbsolutePath() + this.currentFolder;
/*     */ 
/* 186 */       if (!(typeThumbDir.exists()))
/* 187 */         FileUtils.mkdir(typeThumbDir, this.configuration);
/*     */     }
/*     */     catch (SecurityException e) {
/* 190 */       throw new ConnectorException(104, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createThumb()
/*     */     throws ConnectorException
/*     */   {
/* 204 */     this.thumbFile = new File(this.fullCurrentPath, this.fileName);
/*     */     try {
/* 206 */       if (!(this.thumbFile.exists())) {
/* 207 */         File orginFile = new File(((ResourceType)this.configuration.getTypes().get(this.type)).getPath() + this.currentFolder, this.fileName);
/*     */ 
/* 210 */         if (!(orginFile.exists())) {
/* 211 */           throw new ConnectorException(117);
/*     */         }
/*     */         try
/*     */         {
/* 215 */           ImageUtils.createThumb(orginFile, this.thumbFile, this.configuration);
/*     */         } catch (Exception e) {
/* 217 */           this.thumbFile.delete();
/* 218 */           throw new ConnectorException(104, e);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (SecurityException e)
/*     */     {
/* 224 */       throw new ConnectorException(104, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean setResponseHeadersAfterCreatingFile()
/*     */     throws ConnectorException
/*     */   {
/* 239 */     File file = new File(this.fullCurrentPath, this.fileName);
/*     */     try {
/* 241 */       String etag = Long.toHexString(file.lastModified()).concat("-").concat(Long.toHexString(file.length()));
/*     */ 
/* 243 */       if (etag.equals(this.ifNoneMatch)) {
/* 244 */         return false;
/*     */       }
/* 246 */       this.response.setHeader("Etag", etag);
/*     */ 
/* 248 */       if (file.lastModified() <= this.ifModifiedSince) {
/* 249 */         return false;
/*     */       }
/* 251 */       Date date = new Date(System.currentTimeMillis());
/* 252 */       SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss z");
/*     */ 
/* 254 */       this.response.setHeader("Last-Modified", df.format(date));
/*     */ 
/* 256 */       this.response.setContentLength((int)file.length());
/*     */     } catch (SecurityException e) {
/* 258 */       throw new ConnectorException(104, e);
/*     */     }
/*     */ 
/* 261 */     return true;
/*     */   }
/*     */ }

/* Location:           E:\projects\lib\CKFinder-2.0.2.jar
 * Qualified Name:     com.ckfinder.connector.handlers.command.ThumbnailCommand
 * JD-Core Version:    0.5.3
 */