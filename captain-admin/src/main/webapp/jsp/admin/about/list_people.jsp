<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<s:set value="%{getText('admin.navi.headline.title.management.about.people')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>

<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">


<form id="appfrm" class="form-search" action="${ctx}/admin/aboutpeople!list.action" method="post">
<!--  隐藏域 -->
  <s:token />
  <input type="hidden" name="keys"/>
  <input type="hidden" name="oid"/>
  <input type="hidden" name="treeid" value="${param.treeid}"/>
  
  <input type="hidden" name="query_newin" value="${param.query_newin }"/>
  <input type="hidden" name="query_isdesigner" value="${param.query_isdesigner }"/>
<%--  <s:hidden name="pageNo"/>--%>
<%--  <s:hidden name="pageSize"/>--%>
  <s:hidden name="sort"/>
  <s:hidden name="dir"/>
  <s:hidden name="query.type"/>
  <s:hidden name="query.status"/>
  <s:hidden name="query.etitle"/>
  <s:hidden name="query.title"/>
  <s:hidden name="query.publishtimefrom"/>
  <s:hidden name="query.publishtimeto"/>
  <s:hidden name="isAuditPage"/>
  
  <div id="search_div" class="well">
    <div class="row">
      <div class="pull-right">
<%--        <a class="btn" href="javascript:void(0);" onclick="$('#appfrm').submit();"><s:text name="global.button.label.search"/></a>--%>
<%--        <a class="btn" href="javascript:void(0);" onclick="doExport();"><s:text name="global.button.label.export"/></a>--%>
        <a class="btn" href="${ctx}/admin/aboutpeople!edit.action?domain.type=${query.type}&treeid=${param.treeid}" onclick=""><s:text name="global.button.label.add"/></a>
        <a class="btn" href="javascript:void(0);" onclick="doDelete(${param['query.status'] eq 0 ? 2 : 2});"><s:text name="global.button.label.delete"/></a>
      </div>
    </div>
  </div>
  
  <table class="table table-striped">
    <thead>
      <tr>
        <th width="10"><input type="checkbox" id="_checkAll" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
        <th nowrap="true"><s:text name="admin.label.comm.serial"/></th>
        <th><s:text name="admin.thead.comm.chinese.title"/></th>
        <th><s:text name="admin.thead.comm.english.title"/></th>
<%--        <th width=120><s:text name="admin.thead.comm.description"/></th>--%>
      </tr>
    </thead>
    
    <tbody>
    <!-- start page.datas -->
    <s:iterator var="item" value="page.datas" status="stat">
	 <tr ${stat.even ? '' : 'class=""'}>
        <td align="center"><input type="checkbox" name=_checkItem value="${item.oid}" onclick="$('#_checkAll').checkAll(false);"></td>
        <td align="center">
            <a href="${ctx }/admin/aboutpeople!${isAuditPage?'detail':'edit' }.action?oid=${item.oid}" onclick="doEdit('${item.oid}','${isAuditPage?'detail':'edit' }');return false;">
              ${stat.count }
            </a>
        </td>
        <td align="center">
            <a href="${ctx }/admin/aboutcontact!${isAuditPage?'detail':'edit' }.action?oid=${item.oid}" onclick="doEdit('${item.oid}','${isAuditPage?'detail':'edit' }');return false;">
              <s:property value="title"/>
            </a>
        </td>
        <td align="center">
            <a href="${ctx }/admin/aboutcontact!${isAuditPage?'detail':'edit' }.action?oid=${item.oid}" onclick="doEdit('${item.oid}','${isAuditPage?'detail':'edit' }');return false;">
            	<s:property value="etitle"/>
            </a>
        </td>
        <%--
        <td align="center" width="571">
            <a href="${ctx }/admin/aboutpeople!${isAuditPage?'detail':'edit' }.action?oid=${item.oid}" onclick="doEdit('${item.oid}','${isAuditPage?'detail':'edit' }');return false;">
              <s:set value="%{iscn?@com.c306.comm.utils.StringUtil@html2Text(content):@com.c306.comm.utils.StringUtil@html2Text(econtent)}" var="desc"/>
              <s:set value="%{@org.apache.commons.lang.StringEscapeUtils@unescapeHtml(#desc)}" var="desc"/>
              <s:set value="@org.apache.commons.lang.StringUtils@abbreviate(#desc, 500)" var="desc"/>
              <s:property value="%{#desc}"/>
            </a>
        </td>
        --%>
      </tr>
    </s:iterator> <!-- End list iterator -->
    </tbody>
		
  </table>
  
  <div class="pagination pagination-centered">
    <s:property value="%{@com.c306.core.support.PagedUtil@bootstrapNavi(page,#attr.ctx,iscn)}" escapeHtml="false"/>
  </div> <!-- End .pagination -->

</form>

<p></p>
<p></p>



<script type="text/javascript">
function doDelete(delstyle){
	var ids = [];
	$(':checkbox[name=_checkItem]').filter(':checked').each(function(i){
		ids[i] = this.value;
	});
	if(ids.length == 0){
		alert('<s:text name="global.hintinfo.letselectdeleterecord"/>');
		return false;
	}
	if(confirm('<s:text name="global.hintinfo.deleteconfirm"/>')){
		$('#appfrm :input[name=keys]').val(ids.join(','));
		if(delstyle == 1){
			$('#appfrm')[0].action = '${ctx}/admin/aboutpeople!move2trash.action';
		} else if(delstyle == 2){
			$('#appfrm')[0].action = '${ctx}/admin/aboutpeople!delete.action';
		}
		$('#appfrm').submit();
	}
}
//
function doEdit(oid,method){
	$('#appfrm :input[name="oid"]').val(oid);
	$('#appfrm')[0].action = '${ctx}/admin/aboutpeople!'+method+'.action';
	$('#appfrm').submit();
}
function doExport(){
	location = '${ctx}/admin/aboutpeople!exportData.action?'+$('#appfrm').serialize();
}
jQuery.fn.extend({
	checkAll : function(checked) {
		return this.each(function() {
			this.checked = checked;
		});
	}
});
</script>


  </div>
</div><!-- / end tab-content  -->
<%@ include file="/jsp/admin/include/foot.inc"%>