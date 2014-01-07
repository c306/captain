<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<#assign elPrefixStr="${">
<#assign ognlSignSharp="#{">
<#assign ognlSignPercent="%{">

<s:set value="%{getText('i18n.list.title')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>
<%-- set vars --%>
<s:set var="editLink">${elPrefixStr}ctx}/${nameSpace}/${actionName}!edit.action?treeid=${elPrefixStr}param.treeid}</s:set>

<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">


<s:form cssClass="form-search" id="appfrm" action="/${nameSpace}/${actionName}!list.action" method="post" >
<!--  隐藏域 -->
  <input type="hidden" name=treeid value="${elPrefixStr}param.treeid}"/>
  <input type="hidden" name="keys" value=""/>
  <s:hidden name="sort"/>
  <s:hidden name="dir"/>
  
  <div id="search_div" class="well">
	<div class="row">
		<div class="control-group span4">
		  <label><s:text name="i18n.field.title"/>：</label>
		  <s:textfield cssClass="input-large" name="query.title" />
		  <!-- <input type="text" class="input-large" placeholder="" name="query.title" value=""> -->
		</div>
		<div class="control-group span4">
		  <label><s:text name="i18n.field.title"/>：</label>
          <s:select list="${ognlSignSharp}'':'', 'URL':'URL'}" name="query.title"></s:select>
		</div>
	</div>
    <div class="row">
      <div class="pull-right">
        <a class="btn" href="javascript:void(0);" onclick="$('#appfrm').submit();"><s:text name="global.button.label.search"/></a>
        <!--<a class="btn" href="javascript:void(0);" onclick="doExport();"><s:text name="global.button.label.export"/></a>-->
        <a class="btn" href="<s:property value='#editLink'/>" onclick=""><s:text name="global.button.label.add"/></a>
        <a class="btn" href="javascript:void(0);" onclick="doDelete(${elPrefixStr}param['query.status'] eq 0 ? 2 : 2});"><s:text name="global.button.label.delete"/></a>
      </div>
    </div>
  </div>
  
  <table class="table table-striped">
    <thead>
      <tr>
        <th width="30"><input type="checkbox" id="_checkAll" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
        
        <#list fields as x>
        <th><s:text name="i18n.field.${x}"/></th>
        </#list>
        
      </tr>
    </thead>
    
    <tbody>
    <!-- start page.datas -->
    <s:iterator var="item" value="page.datas" status="stat">
	 <tr ${elPrefixStr}stat.even ? '' : 'class=""'}>
        <td align="center">
          <label class="checkbox inline">
            <input type="checkbox" name=_checkItem value="${elPrefixStr}item.${entityPkName}}" onclick="$('#_checkAll').checkAll(false);"> ${elPrefixStr}stat.count }
          </label>
        </td>
        
        <s:set var="editLink"><s:property value="#editLink"/>&oid=<s:property value="${entityPkName}"/></s:set>
        <#list fields as x>
        <td>
	        <a href="<s:property value='#editLink'/>" onclick="doEdit('${elPrefixStr}item.${entityPkName}}','edit');return false;">
	          <s:property value="${x}"/>
	        </a>
        </td>
        </#list>
        
        
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
	doBatchSubmit('${elPrefixStr}ctx}/${nameSpace}/${actionName}!delete.action', '<s:text name="global.hintinfo.deleteconfirm"/>');
}
//
function doEdit(oid,method){
	$('#appfrm :input[name="oid"]').val(oid);
	$('#appfrm')[0].action = '${ctx}/acegi/user!'+method+'.action';
	$('#appfrm').submit();
}
//
function doExport(){
	location = '${elPrefixStr}ctx}/${nameSpace}/${actionName}!exportData.action?'+$('#appfrm').serialize();
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