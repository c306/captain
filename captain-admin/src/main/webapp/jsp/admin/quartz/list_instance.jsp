<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<s:set value="%{getText('admin.navi.headline.title.quartz.joblist')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>

<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">


<form id="appfrm" class="form-search" action="${ctx}/quartz/scheduler!instanceList.action" method="post">
<!--  隐藏域 -->
  <input type="hidden" name=treeid value="${param.treeid }"/>
  <input type="hidden" name="keys" value=""/>
<%--  <s:hidden name="pageNo"/>--%>
<%--  <s:hidden name="pageSize"/>--%>
  <s:hidden name="sort"/>
  <s:hidden name="dir"/>
  <s:hidden name="query.type"/>
  
  <div id="search_div" class="well">
    <div class="row">
      <div class="pull-right">
        <a class="btn btn-primary" href="javascript:void(0);" onclick="$('#appfrm').submit();"><s:text name="global.button.label.refresh"/></a>
        <!--<a class="btn" href="javascript:void(0);" onclick="doExport();"><s:text name="global.button.label.export"/></a>-->
        <a class="btn" href="javascript:void(0);" onclick="doPause();"><s:text name="admin.label.quartz.pausescheduler"/></a>
        <a class="btn" href="javascript:void(0);" onclick="doResume();"><s:text name="admin.label.quartz.resumecheduler"/></a>
      </div>
    </div>
  </div>
  
  <table class="table table-striped">
    <thead>
      <tr>
        <th width="30"><input type="checkbox" id="_checkAll" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
        <th><s:text name="admin.label.quartz.instance"/></th>
        <th><s:text name="admin.thead.comm.description"/></th>
      </tr>
    </thead>
    
    <tbody>
    <!-- start page.datas -->
    <s:iterator var="item" value="#request.schedulerList" status="stat">
	 <tr ${stat.even ? '' : 'class=""'}>
        <td align="center">
          <label class="checkbox inline">
            <input type="checkbox" name=_checkItem value="${item.schedulerName}" onclick="$('#_checkAll').checkAll(false);"> ${stat.count }
          </label>
        </td>
        <td align="center">
          <s:property value="schedulerName"/><br />
          <s:property value="node"/>
        </td>
        <td align="center">
          Version: <s:property value="version"/>
          <br />
          JobStore: <s:property value="jobStoreClassName"/>
          <br />
          ThreadPool: <s:property value="threadPoolClassName"/>
          <br />
          ThreadPoolSize: <s:property value="threadPoolSize"/>
          <br />
          State: <s:if test="!standbyMode"><span id="state_span" class="badge badge-success">EXECUTING</span></s:if>
          <s:else><span id="state_span" class="badge badge-warning">STANDBY</span></s:else>
        </td>
      </tr>
    </s:iterator> <!-- End list iterator -->
    </tbody>
		
  </table>

<%--  <div class="pagination pagination-centered">--%>
<%--    <s:property value="%{@com.c306.core.support.PagedUtil@bootstrapNavi(page,#attr.ctx,iscn)}" escapeHtml="false"/>--%>
<%--  </div> <!-- End .pagination -->--%>


</form>

<p></p>
<p style="float: right;">Client Default TimeZone: <a href="${ctx}/common/sysinfo.jsp"><%=java.util.TimeZone.getDefault().getID() %></a></p>

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
		/*
		$('#appfrm :input[name=keys]').val(ids.join(','));
		$('#appfrm')[0].action = actionUrl;
		$('#appfrm').submit();
		*/
		$.each( ids, function(i, key){
			doAjax(actionUrl, key);
		});
	}
}
// ajax request
function doAjax(actionUrl, key) {
	//alert($.param(params));
	showProgressbar(false);
	// 可能需要跨域访问(如果url不再同一域)
	$.getJSON(actionUrl, {keys: key},
		function(data){
		  showProgressbar(true);
          //
          if(data.success === true) {
          	//showMsg("Successfully.", 1, 1);
          	location.reload();
          } else {
        	showMsg( key + " 处理失败: " + data.jsonMap.msg, 1, 2);
          }
		});
}
//暂停
function doPause(){
	doBatchSubmit('${ctx}/quartz/scheduler!pauseInstance.action', '<s:text name="admin.label.quartz.job.pauseinstance.hitinfo"/>');
}
//恢复启用
function doResume(){
	doBatchSubmit('${ctx}/quartz/scheduler!resumeInstance.action', '<s:text name="admin.label.quartz.job.resumeinstance.hitinfo"/>');
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