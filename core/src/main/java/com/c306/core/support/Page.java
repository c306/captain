package com.c306.core.support;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.criterion.Order;

/**
 * 分页对象.
 * 包含当前页数据及分页信息.
 */
public class Page {
	private int pageSize = 10;// 每页显示的行数
	private int recordCount;// 总共的行数
	private int pageNo;// 将要显示页面
	
	private String sort="oid"; //排序用
	private String dir="ASC"; //排序用
	
	private Collection datas = new ArrayList(0);//　当前页中存放的记录

	/**
	 * 构造方法
	 * @param pageSize　每页显示的行数， 当pageSize<1时，取默认大小：Constant.PageControl.CODE_PAGELINECOUNT
	 * @param pageNo　转到第pageNo页
	 */
	public Page(int pageSize, int pageNo, int totalCount) {
		if(pageSize > 0)
			this.pageSize = pageSize;
		this.recordCount = totalCount;
		setPageNo(pageNo);
	}

	// 总页数
	public int getPageCount() {
		int size = recordCount / pageSize;// 总条数/每页显示的条数=总页数
		int mod = recordCount % pageSize;// 最后一页的条数
		if (mod != 0)
			size++;
		return recordCount == 0 ? 1 : size;
	}

	// 包含，起始索引为0
	public int getFromIndex() {
		return (getPageNo() - 1) * pageSize;
	}
	
	//起始索引，从0开始
	public static int getFromIndex(int pageNo, int pageSize) {
		if(pageNo<1) return 0;
		return (pageNo - 1) * pageSize;
	}

	// 不包含
	public int getToIndex() {
		return Math.min(recordCount, getPageNo() * pageSize);
	}

	// 得到当前页
	public int getPageNo() {
		int validPage = pageNo <= 0 ? 1 : pageNo;
		validPage = validPage > getPageCount() ? getPageCount() : validPage;
		return validPage;
	}
	
	// 设置当前页
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	// 得到每页显示的条数
	public int getPageSize() {
		return pageSize;
	}
	
	// 设置每页显示的条数
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	// 得到总共的条数
	public int getRecordCount() {
		return recordCount;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	
	
	public Collection getDatas() {
		return datas;
	}

	public void setDatas(Collection datas) {
		this.datas = datas;
	}
	
	@JSON(deserialize=true)
	public String getNavi(String ctx) {
		StringBuilder html = new StringBuilder("<div id='Pagination_div'>");
		
		//当前页大于第一页时
		if(this.getPageNo() > 1){
			html.append("<a href='javascript:void(0);' onclick='Pagination.pageOperate(1,this)'>首页</a> " +
					"<a href='javascript:void(0);' onclick='Pagination.pageOperate(" + (this.getPageNo()-1) + ",this)'>上一页</a> ");
		} else {
			html.append("<a href='javascript:void(0);' disabled>首页</a> " +
					"<a href='javascript:void(0);' disabled>上一页</a> ");
		}
		//当前页小于最后一页
		if(this.getPageNo() < this.getPageCount()){
			html.append("<a href='javascript:void(0);' onclick='Pagination.pageOperate(" + (this.getPageNo()+1) + ",this)'>下一页</a> " +
					"<a href='javascript:void(0);' onclick='Pagination.pageOperate(" + this.getPageCount() + ",this)'>末页</a> ");
		} else {
			html.append("<a href='javascript:void(0);' disabled>下一页</a> " +
					"<a href='javascript:void(0);' disabled>末页</a> ");
		}

		int [] opts = new int[]{5, 10, 20, 40, 100};
		//每页 20行    转到第 3 页  共3页，99行
		html.append("每页 <select name='pageSize' onchange=\"Pagination.pageSizeChangeHandler(this)\">");
		for(int tmp : opts){
			html.append("<option value='" + tmp + "' " + (tmp==getPageSize()?"selected='true'":"") + ">" + tmp + "</option>");
		}
		html.append("</select>行");
		
		html.append("&nbsp;&nbsp;&nbsp;&nbsp;转到第<input type='text' id='__Pagination_PageNo' name='pageNo' value='" + this.getPageNo() + "' size='4' onkeyup='Pagination.pageNoChangeHandler()'  title='输入目标页码'/>" +
				"<input type='button' class='Pagination_goto_btn' id='Pagination_go' onclick='Pagination.pageOperate(document.getElementById(\"__Pagination_PageNo\").value,this)' title='点击转到目标页' value='go'/>页&nbsp;&nbsp;共" +
				this.getPageCount() + "页，" + this.getRecordCount() + "行" +
				"</div>");
		//引入外部js文件
		html.append("<script src=\"");
		html.append(ctx);
		html.append("/common/js/paginghandler.js\" type=\"text/javascript\"></script>");
		return html.toString();
	}
	

	public Order getOrder(){
		if(!GenericValidator.isBlankOrNull(this.getSort()))
			if("ASC".equalsIgnoreCase(this.getDir()))
				return Order.asc(this.getSort());
			else
				return Order.desc(this.getSort());
		return null;
	}

}
