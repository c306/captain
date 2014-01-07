<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>


<s:set value="%{getText('i18n.list.title')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>
<%-- set vars --%>
<s:set var="editLink">${ctx}/acegi/user!edit.action?treeid=${param.treeid}</s:set>

<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">

<%@ include file="/jsp/admin/include/alert.inc"%>

<s:form cssClass="form-search" id="appfrm" action="/acegi/user!list.action" method="post" >
<!--  隐藏域 -->
  <input type="hidden" name=treeid value="${param.treeid}"/>
  <input type="hidden" name="keys" value=""/>
  <input type="hidden" name="oid" value=""/>
  <s:hidden name="sort"/>
  <s:hidden name="dir"/>
  
  <div id="search_div" class="well">
	<div class="row">
		<div class="control-group span4">
		  <label><s:text name="i18n.field.username"/>：</label>
		  <s:textfield cssClass="input-large" name="query.username" />
		  <!-- <input type="text" class="input-large" placeholder="" name="query.username" value=""> -->
		</div>
		<div class="control-group span4">
		  <label><s:text name="i18n.field.email"/>：</label>
          <s:textfield cssClass="input-large" name="query.email" />
		</div>
	</div>
    <div class="row">
      <div class="pull-right">
        <a class="btn" href="javascript:void(0);" onclick="$('#appfrm').submit();"><s:text name="global.button.label.search"/></a>
        <!--<a class="btn" href="javascript:void(0);" onclick="doExport();"><s:text name="global.button.label.export"/></a>-->
        <a class="btn" href="<s:property value='#editLink'/>" onclick=""><s:text name="global.button.label.add"/></a>
        <a class="btn" href="javascript:void(0);" onclick="doDelete(${param['query.status'] eq 0 ? 2 : 2});"><s:text name="global.button.label.delete"/></a>
      </div>
    </div>
  </div>
  
  <table class="table table-striped">
    <thead>
      <tr>
        <th width="30"><input type="checkbox" id="_checkAll" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
        
        <th><s:text name="i18n.field.username"/></th>
        <th><s:text name="i18n.field.firstName"/></th>
        <th><s:text name="i18n.field.lastName"/></th>
        <th><s:text name="i18n.field.email"/></th>
        <th><s:text name="i18n.field.mobile"/></th>
        <th><s:text name="i18n.field.accountLocked"/></th>
        <%-- <th><s:text name="i18n.field.accountExpired"/></th>
        <th><s:text name="i18n.field.credentialsExpired"/></th> --%>
        
      </tr>
    </thead>
    
    <tbody>
    <!-- start page.datas -->
    <s:iterator var="item" value="page.datas" status="stat">
	 <tr ${stat.even ? '' : 'class=""'}>
        <td align="center">
          <label class="checkbox inline">
            <input type="checkbox" name=_checkItem value="${item.id}" onclick="$('#_checkAll').checkAll(false);"> ${stat.count }
          </label>
        </td>
        
        <s:set var="editLink"><s:property value="#editLink"/>&oid=<s:property value="id"/></s:set>
        <td>
	        <a href="<s:property value='#editLink'/>" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="username"/>
	        </a>
        </td>
        <td>
	        <a href="<s:property value='#editLink'/>" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="firstName"/>
	        </a>
        </td>
        <td>
	        <a href="<s:property value='#editLink'/>" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="lastName"/>
	        </a>
        </td>
        <td>
	        <a href="<s:property value='#editLink'/>" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="email"/>
	        </a>
        </td>
        <td>
	        <a href="<s:property value='#editLink'/>" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="mobile"/>
	        </a>
        </td>
        <td>
	        <a href="<s:property value='#editLink'/>" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="accountLocked"/>
	        </a>
        </td>
        <%-- <td>
	        <a href="<s:property value='#editLink'/>" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="accountExpired"/>
	        </a>
        </td>
        <td>
	        <a href="<s:property value='#editLink'/>" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="credentialsExpired"/>
	        </a>
        </td> --%>
        
      </tr>
    </s:iterator> <!-- End list iterator -->
    </tbody>
		
  </table>

	<div class="pagination pagination-centered">
	  <s:property value="%{@com.c306.core.support.PagedUtil@bootstrapNavi(page,#attr.ctx,iscn)}" escapeHtml="false"/>
	</div> <!-- End .pagination -->


</s:form>

<p></p>
<p></p>


<!-- dialogs.div -->
<div style="display:none;">
<style type="text/css">
</style>

</div><!-- dialog.div display:none; -->

<script type="text/javascript">
// dialogs init script
$(function($){
// initial code here
});
</script>
<!-- // end dialog -->



<script type="text/javascript">
//删除
function doDelete(){
	doBatchSubmit('${ctx}/acegi/user!delete.action', '<s:text name="global.hintinfo.deleteconfirm"/>');
}
//
function doEdit(oid,method){
	$('#appfrm :input[name="oid"]').val(oid);
	$('#appfrm')[0].action = '${ctx}/acegi/user!'+method+'.action';
	$('#appfrm').submit();
}
//
function doExport(){
	location = '${ctx}/acegi/user!exportData.action?'+$('#appfrm').serialize();
}
//
function doBatchSubmit(actionUrl, confirmInfo){
	var ids = [];
	$(':checkbox[name=_checkItem]').filter(':checked').each(function(i){
		ids[i] = this.value;
	});
	if(ids.length == 0){
		alert('<s:text name="global.hintinfo.pleseselectrecord"/>');
		return false;
	}
	if(confirm(confirmInfo)){
		$('#appfrm :input[name=keys]').val(ids.join(','));
		$('#appfrm')[0].action = actionUrl;
		$('#appfrm').submit();
	}
}
//////////utils//////////////////////
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