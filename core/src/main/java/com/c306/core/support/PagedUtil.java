package com.c306.core.support;


public class PagedUtil {
	
	public static String [] First = new String[]{"首页", "« First"};
	public static String [] Previous = new String[]{"上一页", "« Previous"};
	public static String [] Next = new String[]{"下一页", "Next »"};
	public static String [] Last = new String[]{"末页", "Last »"};
	public static String [] Epage = new String[]{"每页", "Page"};
	public static String [] Item = new String[]{"行", "Item"};
	

	/**
	 * 默认7
	 * @param page
	 * @param ctx
	 * @param iscn
	 * @return
	 */
	public static String greenNavi(Page page, String ctx, boolean iscn){
		return greenNavi(page, ctx, 7, iscn);
	}
	/**
	 * 
	 * @param page
	 * @param ctx
	 * @param lenth
	 * @param iscn
	 * @return
	 */
	public static String greenNavi(Page page, String ctx, int lenth, boolean iscn){
		int idx = 1;
		if(iscn)
			idx = 0;
		StringBuilder html = new StringBuilder("");
		
		//当前页大于第一页时
		if(page.getPageNo() > 1){
			html.append("<a href='javascript:void(0);' onclick='Pagination.pageOperate(1,this)'>" + First[idx] + "</a> " +
					"<a href='javascript:void(0);' onclick='Pagination.pageOperate(" + (page.getPageNo()-1) + ",this)'>" + Previous[idx] + "</a> ");
		} else {
			html.append("<a href='javascript:void(0);' disabled>" + First[idx] + "</a> " +
					"<a href='javascript:void(0);' disabled>" + Previous[idx] + "</a> ");
		}
		
		int harf = lenth/2;
		
		int sta = page.getPageCount() - page.getPageNo() > harf ? page.getPageNo() - harf : page.getPageCount() - lenth + 1;
		sta = sta < 1 ? 1 : sta;
		int end = sta + lenth - 1 < page.getPageCount() ? sta + lenth - 1 : page.getPageCount();
		
		for(int i = sta; i<=end; i++){
			if(i == page.getPageNo())
				html.append("<a href='javascript:void(0);' class='number current' onclick='Pagination.pageOperate(" + i + ",this)'>" + i + "</a> ");
			else
				html.append("<a href='javascript:void(0);' class='number' onclick='Pagination.pageOperate(" + i + ",this)'>" + i + "</a> ");
		}
		
		//当前页小于最后一页
		if(page.getPageNo() < page.getPageCount()){
			html.append("<a href='javascript:void(0);' onclick='Pagination.pageOperate(" + (page.getPageNo()+1) + ",this)'>" + Next[idx] + "</a> " +
					"<a href='javascript:void(0);' onclick='Pagination.pageOperate(" + page.getPageCount() + ",this)'>" + Last[idx] + "</a> ");
		} else {
			html.append("<a href='javascript:void(0);' disabled>" + Next[idx] + "</a> " +
					"<a href='javascript:void(0);' disabled>" + Last[idx] + "</a> ");
		}
		
		int [] opts = new int[]{5, 10, 20, 40, 100, 500};
		//每页 20行    转到第 3 页  共3页，99行
		html.append(Epage[idx] + "<select name='query.pageSize' style='width:50px;' onchange=\"Pagination.pageSizeChangeHandler(this)\">");
		for(int tmp : opts){
			html.append("<option value='" + tmp + "' " + (tmp==page.getPageSize()?"selected='true'":"") + ">" + tmp + "</option>");
		}
		html.append("</select>" + Item[idx] + "&nbsp;&nbsp;&nbsp;&nbsp;");
		
		html.append(page.getPageNo() + "/" + page.getPageCount());
		
		html.append("<input type='hidden' id='__Pagination_PageNo' name='query.pageNo' value='" + page.getPageNo() + "'/>");
		//引入外部js文件
		html.append("<script src=\"");
		html.append(ctx);
		html.append("/lookfor/common/js/paginghandler.js\" type=\"text/javascript\"></script>");
		return html.toString();
	}
	
	

	/**
	 * 默认9
	 * @param page
	 * @param ctx
	 * @param iscn
	 * @return
	 */
	public static String bootstrapNavi(Page page, String ctx, boolean iscn){
		return bootstrapNavi(page, ctx, 7, iscn);
	}
	/**
	 * 
	 * @param page
	 * @param ctx
	 * @param lenth
	 * @param iscn
	 * @return
	 */
	public static String bootstrapNavi(Page page, String ctx, int lenth, boolean iscn){
		int idx = 1;
		if(iscn)
			idx = 0;
		StringBuilder html = new StringBuilder("<ul>");
		
		//当前页大于第一页时
		if(page.getPageNo() > 1){
			html.append("<li><a href='javascript:void(0);' onclick='Pagination.pageOperate(1,this)'>" + First[idx] + "</a></li> " +
					"<li><a href='javascript:void(0);' onclick='Pagination.pageOperate(" + (page.getPageNo()-1) + ",this)'>" + Previous[idx] + "</a></li> ");
		} else {
			html.append("<li class='disabled'><a href='javascript:void(0);'>" + First[idx] + "</a></li> " +
					"<li class='disabled'><a href='javascript:void(0);'>" + Previous[idx] + "</a></li> ");
		}
		
		int harf = lenth/2;
		
		int sta = page.getPageCount() - page.getPageNo() > harf ? page.getPageNo() - harf : page.getPageCount() - lenth + 1;
		sta = sta < 1 ? 1 : sta;
		int end = sta + lenth - 1 < page.getPageCount() ? sta + lenth - 1 : page.getPageCount();
		
		for(int i = sta; i<=end; i++){
			if(i == page.getPageNo())
				html.append("<li class='active'><a href='javascript:void(0);' onclick='void(0)'>" + i + "</a></li> ");
			else
				html.append("<li><a href='javascript:void(0);' onclick='Pagination.pageOperate(" + i + ",this)'>" + i + "</a></li> ");
		}
		
		//当前页小于最后一页
		if(page.getPageNo() < page.getPageCount()){
			html.append("<li><a href='javascript:void(0);' onclick='Pagination.pageOperate(" + (page.getPageNo()+1) + ",this)'>" + Next[idx] + "</a></li> " +
					"<li><a href='javascript:void(0);' onclick='Pagination.pageOperate(" + page.getPageCount() + ",this)'>" + Last[idx] + "</a></li> ");
		} else {
			html.append("<li class='disabled'><a href='javascript:void(0);' disabled>" + Next[idx] + "</a></li> " +
					"<li class='disabled'><a href='javascript:void(0);' disabled>" + Last[idx] + "</a></li> ");
		}
		
		html.append("</ul>");

		html.append("<input type='hidden' id='__Pagination_PageNo' name='query.pageNo' value='" + page.getPageNo() + "'/>");
		
		//引入外部js文件
		html.append("<script src=\"");
		html.append(ctx);
		html.append("/lookfor/common/js/paginghandler.js\" type=\"text/javascript\"></script>");
		return html.toString();
	}
	
	

}
