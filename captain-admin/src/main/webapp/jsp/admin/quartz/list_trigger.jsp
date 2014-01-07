<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<s:set value="%{getText('admin.navi.headline.title.quartz.triggerlist')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>

<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">


<form id="appfrm" class="form-search" action="${ctx}/quartz/scheduler!triggerList.action" method="post">
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
		<div class="control-group span4">
		  <label><s:text name="admin.label.quartz.trigger.group"/>：</label>
		  <input type="text" class="input-large" placeholder="" id="triggerGroup" name="triggerGroup" value="<s:property value="triggerGroup.trim()"/>">
		</div>
		<div class="control-group span4">
		  <label><s:text name="admin.label.quartz.trigger.name"/>：</label>
          <input type="text" class="input-large" placeholder="" id="triggerName" name="triggerName" value="<s:property value="triggerName.trim()"/>">
		</div>
	</div>
    <div class="row">
      <div class="pull-right">
        <a class="btn" href="javascript:void(0);" onclick="$('#appfrm').submit();" id="searchBtn"><s:text name="global.button.label.search"/></a>
        <!--<a class="btn" href="javascript:void(0);" onclick="doExport();"><s:text name="global.button.label.export"/></a>-->
        <a class="btn" href="javascript:void(0);" onclick="doPauseTriggers();"><s:text name="admin.label.quartz.trigger.pausetriggers"/></a>
        <a class="btn" href="javascript:void(0);" onclick="doResumeTriggers();"><s:text name="admin.label.quartz.trigger.resumetriggers"/></a>
        <a class="btn" href="javascript:void(0);" onclick="doDeleteTriggers(${param['query.status'] eq 0 ? 2 : 2});"><s:text name="global.button.label.delete"/></a>
      </div>
    </div>
  </div>
  
  <table class="table table-striped">
    <thead>
      <tr>
        <th width="30"><input type="checkbox" id="_checkAll" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
        <th><s:text name="admin.label.quartz.trigger.group"/>.<s:text name="admin.label.quartz.trigger.name"/></th>
        <th><s:text name="admin.label.quartz.trigger.triggerinfo"/></th>
        <th width="265"><s:text name="admin.label.quartz.trigger.start2endtime"/></th>
        <th><s:text name="admin.label.quartz.trigger.description"/></th>
      </tr>
    </thead>
    
    <tbody>
    <!-- start page.datas -->
    <s:iterator var="item" value="#request.triggerList" status="stat">
	 <tr ${stat.even ? '' : 'class=""'}>
        <td>
          <label class="checkbox inline">
            <input type="checkbox" name=_checkItem value="${item.group}/${item.name}" onclick="$('#_checkAll').checkAll(false);"> ${stat.count }
          </label>
        </td>
        <td>
          <s:property value="group"/>.<s:property value="name"/>
        </td>
        <td>
            Priority:<s:property value="priority"/>, 
            <s:if test="cronExpression != null">
            CronExp:<s:property value="cronExpression"/>
            </s:if>
            <s:else>
            Interval:<s:property value="repeatInterval / 1000"/>, Count:<s:property value="repeatCount"/>
            </s:else>
            <br />
            TriggerData:[<s:property value="@com.c306.core.quartz.JobInfoSupport@getJobDataForString(jobDataMap, ', ')"/>]
            <br />
            State:
            <span class="badge <s:property value="#request.trigStateSet.get(key.toString()) == 'PAUSED' ? 'badge-warning' : ''"/>">
              <s:property value="#request.trigStateSet.get(key.toString())"/>
            </span>
            <span id="k_<s:property value="group"/>_<s:property value="name"/>" style="display:none;"><s:property value="#request.triggersJsonMap[#triggerDetail.getKey()]" escapeHtml="false" /></span>
        </td>
        <td align="center">
        <!-- s:date useage, Condition 3: With nice attribute as false and no format attribute is specified (file:///F:/org/Struts2.x/struts-2.2.1.1/docs/WW/date.html) -->
              <s:if test="previousFireTime!=null"><s:date name="previousFireTime"/></s:if>
              <s:else>____-__-__ __:__:__</s:else>
              /
              <s:if test="nextFireTime!=null"><s:date name="nextFireTime"/></s:if>
              <s:else>____-__-__ __:__:__</s:else>
              <br />
              <s:if test="startTime!=null"><s:date name="startTime"/></s:if>
              <s:else>____-__-__ __:__:__</s:else>
              /
              <s:if test="endTime!=null"><s:date name="endTime"/></s:if>
              <s:else>____-__-__ __:__:__</s:else>
        </td>
        <td>
              <s:property value="description"/>
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
///////////////////
// delete trigger
function deleteTrigger(trigGroup,trigName) {
	if(!confirm('<s:text name="global.hintinfo.operateconfirm"/>')) return;
}
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
		showProgressbar(false);
		$.post(
			actionUrl,
			{keys: ids.join(',')},
			function(data){
			  showProgressbar(true);
	          //
	          if(data.success === true) {
	          	//showMsg("Successfully.", 1, 1);
	          	alert("Successfully.");
	            $('#searchBtn').click();
	          } else
	          	showMsg(data.jsonMap.msg, 1, 2);
			}, "json");
	}
}
//暂停Trigger
function doPauseTriggers(){
	doBatchSubmit('${ctx}/quartz/scheduler!pauseTriggers.action', '<s:text name="global.hintinfo.operateconfirm"/>');
}
//恢复启用Trigger
function doResumeTriggers(delstyle){
	doBatchSubmit('${ctx}/quartz/scheduler!resumeTriggers.action', '<s:text name="global.hintinfo.operateconfirm"/>');
}
//删除Trigger
function doDeleteTriggers(){
	doBatchSubmit('${ctx}/quartz/scheduler!deleteTriggers.action', '<s:text name="global.hintinfo.deleteconfirm"/>');
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