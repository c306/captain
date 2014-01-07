package com.c306.code.masonry.action;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.c306.code.article.service.ArticleService;
import com.c306.code.article.service.PictureService;
import com.c306.code.masonry.service.MasonryBrickService;
import com.c306.core.base.action.BaseGenericActionSupport;
import com.c306.core.fileupload.entity.Attachment;
import com.c306.hbm.TMasonryBrick;


public class MasonryBrickAction extends BaseGenericActionSupport<TMasonryBrick> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private MasonryBrickService service;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private PictureService pictureService;

	private Attachment pic = new Attachment();
	
	@Override
	protected MasonryBrickService getService() {
		return service;
	}

	@Override
	public String execute() throws Exception {
		// mansory
		this.query.setPageNo(1);
		this.query.setPageSize(1000);
		this.items = (List) this.service.pagedQuery(this.query).getDatas();
		
		// candidate
		this.page = pictureService.findCandidates(pic, 1, 300);
		
		return SUCCESS;
	}
	
	public String save() throws Exception {
		
		logger.info("save json : " + this.keys);

		JSONArray jsonArray= JSONArray.fromObject(this.keys);
		List<TMasonryBrick> list = JSONArray.toList(jsonArray, TMasonryBrick.class);
		
		this.service.saveOrUpdateAll(list);
		
		this.setSuccess(true);
		
		return AJAX;
	}
	
	public String candidate() throws Exception {
		return "candidate";
	}
	
	public String gallery() throws Exception {
		// mansory
		if(StringUtils.isNotBlank(this.keys)) {// preview 
			logger.info("preview json : " + this.keys);
			JSONArray jsonArray= JSONArray.fromObject(this.keys);
			// preview masonry bricks
			this.items = JSONArray.toList(jsonArray, TMasonryBrick.class);
		} else {
			// query masonry bricks
			this.query.setPageSize(20);
			this.page = this.service.pagedQuery(this.query);
			this.items = (List) this.page.getDatas();
		}
		
		return "gallery";
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String removeCandidates() throws Exception {
		
		this.service.removeCandidates(this.keys.split(","));

		this.setSuccess(true);
		return AJAX;
	}
	
}
