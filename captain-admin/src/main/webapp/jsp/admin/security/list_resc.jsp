<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<s:set value="%{getText('resc.title')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>

<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">

<%@ include file="/jsp/admin/include/alert.inc"%>

<form id="appfrm" class="form-search" action="${ctx}/acegi/resc!list.action" method="post">
<!--  隐藏域 -->
  <input type="hidden" name=treeid value="${param.treeid }"/>
  <input type="hidden" name="keys" value=""/>
  <input type="hidden" name="oid" value=""/>
  <s:hidden name="sort"/>
  <s:hidden name="dir"/>
  
  <div id="search_div" class="well">
      <div class="row">
		<div class="control-group span4">
		  <label><s:text name="resc.resstring"/>：</label>
		  <s:textfield cssClass="input-large" name="query.resString" />
		  <!-- <input type="text" class="input-large" placeholder="" name="query.resString" value=""> -->
		</div>
		<div class="control-group span4">
		  <label><s:text name="resc.restype"/>：</label>
          <s:select list="#{'':'', 'URL':'URL'}" name="query.resType"></s:select>
		</div>
	</div>
    <div class="row">
      <div class="pull-right">
        <a class="btn" href="javascript:void(0);" onclick="$('#appfrm').submit();"><s:text name="global.button.label.search"/></a>
        <!--<a class="btn" href="javascript:void(0);" onclick="doExport();"><s:text name="global.button.label.export"/></a>-->
        <a class="btn" href="${ctx}/acegi/resc!edit.action?treeid=${param.treeid}" onclick=""><s:text name="global.button.label.add"/></a>
        <a class="btn" href="javascript:void(0);" onclick="doDelete(${param['query.status'] eq 0 ? 2 : 2});"><s:text name="global.button.label.delete"/></a>
      </div>
    </div>
  </div>
  
  <table class="table table-striped">
    <thead>
      <tr>
        <th width="30"><input type="checkbox" id="_checkAll" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
        <th><s:text name="resc.name"/></th>
        <th><s:text name="resc.resstring"/></th>
        <th><s:text name="resc.restype"/></th>
        <th><s:text name="resc.priority"/></th>
        <th><s:text name="resc.descn"/></th>
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
        <td>
	        <a href="${ctx}/acegi/resc!edit.action?oid=${item.id}&treeid=${param.treeid}" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="name"/>
	        </a>
        </td>
        <td>
	        <a href="${ctx}/acegi/resc!edit.action?oid=${item.id}&treeid=${param.treeid}" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="resString"/>
	        </a>
        </td>
        <td>
        	<s:property value="resType"/>
        </td>
        <td>
        	<s:property value="priority"/>
        </td>
        <td>
        	<s:property value="descn"/>
        </td>
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


<!-- dialogs -->
<div style="display:none;">
<style type="text/css">
</style>
<!-- dialog-message -->
<div id="dialog-message" title="Information" style="padding-top:15px;">
  <div id="dialog-message-cnt"></div>
</div><!-- dialog-message //end -->
</div><!-- dialog.div display:none; -->
<script type="text/javascript">
// dialogs init script
$(function($){
  $( "#dialog-message" ).dialog({
      modal: true,
      autoOpen: false,
      maxHeight: 300,
      buttons: {
        Ok: function() {
          $( this ).dialog( "close" );
        }
      }
    });
});
</script>
<!-- // end trigger script -->



<script type="text/javascript">
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
//删除
function doDelete(){
	doBatchSubmit('${ctx}/acegi/resc!delete.action', '<s:text name="global.hintinfo.deleteconfirm"/>');
}
//
function doEdit(oid,method){
	$('#appfrm :input[name="oid"]').val(oid);
	$('#appfrm')[0].action = '${ctx}/acegi/resc!'+method+'.action';
	$('#appfrm').submit();
}
//
function doExport(){
	location = '${ctx}/admin/products!exportData.action?'+$('#appfrm').serialize();
}
//////////utils//////////////////////
jQuery.fn.extend({
	checkAll : function(checked) {
		return this.each(function() {
			this.checked = checked;
		});
	}
});
//dialog-message
function showMsg(msg, isModal, success) {
	var icon = '', clas = '';
	if(success && success === 1){
		icon = '<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>';
		//clas = 'ui-state-error ui-corner-all';
	} else if(success) {
		icon = '<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>';
		clas = 'ui-state-error ui-corner-all';
	}
	$( "#dialog-message" ).find('#dialog-message-cnt').html('<div class="' + clas + '">' + icon + msg + '</div>').parent().dialog( "open" );
}
function showProgressbar(hide) {
	if(hide) {
		$("#ui-progressbar").remove();
		$("#overlay_div").remove();
		return;
	}
	$(document.body).append('<div id="ui-progressbar" class="progress progress-striped active ui-state-highlight" style="position:fixed;width:350px;z-index:1030;top:'+($(window).height()-30)/2+'px;left:'+($(window).width()-350)/2+'px;"><div class="bar" style="width: 100%;"></div></div>');
	//$(document.body).append('<div id="ui-progressbar" style="position:fixed;width:350px;z-index:1030;top:'+($(window).height()-30)/2+'px;left:'+($(window).width()-350)/2+'px;"></div>');
	$(document.body).append('<div id="overlay_div" class="bar" style="position:absolute;top:0px;left:0px;z-index:1000;0px;width:'+$(document).width()+'px;height:'+$(document).height()+'px;"></div>');
	return;
}
</script>


  </div>
</div><!-- / end tab-content  -->
<%@ include file="/jsp/admin/include/foot.inc"%>