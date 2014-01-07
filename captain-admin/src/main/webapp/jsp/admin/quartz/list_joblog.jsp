<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<s:set value="%{getText('admin.navi.headline.title.quartz.joblog')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>

<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">


<form id="appfrm" class="form-search" action="${ctx}/quartz/joblog!list.action" method="post">
<!--  隐藏域 -->
  <input type="hidden" name=treeid value="${param.treeid }"/>
  <input type="hidden" name="keys" value=""/>
<%--  <s:hidden name="pageNo"/>--%>
<%--  <s:hidden name="pageSize"/>--%>
  <s:hidden name="sort"/>
  <s:hidden name="dir"/>
  <!-- Pagination size param -->
  <s:hidden name="query.pageSize"/>
<!--  隐藏域 -->
  <s:token />
  <input type="hidden" name="asd" value="${sessionScope.__SecUser.id}"/>
  <input type="hidden" name="asd" value="${sessionScope.__SecUser.username}"/>
  
  <div id="search_div" class="well">
      <div class="row">
		<div class="control-group span4">
		  <label><s:text name="admin.label.quartz.job.group"/>：</label>
		  <input type="text" class="input-large" placeholder="" id="jobGroup" name="query.jobGroup" value="<s:property value="query.jobGroup.trim()"/>">
		</div>
		<div class="control-group span4">
		  <label><s:text name="admin.label.quartz.job.name"/>：</label>
          <input type="text" class="input-large" placeholder="" id="jobName" name="query.jobName" value="<s:property value="query.jobName.trim()"/>">
		</div>
	</div>
    <div class="row">
      <div class="pull-right">
        <a class="btn" href="javascript:void(0);" onclick="$('#appfrm').submit();" id="searchBtn"><s:text name="global.button.label.search"/></a>
        <!--<a class="btn" href="javascript:void(0);" onclick="doExport();"><s:text name="global.button.label.export"/></a>-->
      </div>
    </div>
  </div>
  
  <table class="table table-striped">
    <thead>
      <tr>
        <th width="30"></th>
        <th><s:text name="admin.label.quartz.job.group"/>.<s:text name="admin.label.quartz.job.name"/></th>
        <th>IP</th>
        <th><s:text name="admin.label.quartz.log.status"/></th>
        <th><s:text name="admin.label.quartz.log.firetime"/></th>
        <th><s:text name="admin.label.quartz.log.endtime"/></th>
        <th><s:text name="admin.label.quartz.log.runtime"/></th>
        <th><s:text name="admin.label.quartz.log.triggerinfo"/></th>
        <th><s:text name="admin.label.quartz.log.jobdatamap"/></th>
      </tr>
    </thead>
    
    <tbody>
    <!-- start page.datas -->
    <s:iterator var="item" value="page.datas" status="stat">
	 <tr ${stat.even ? '' : 'class=""'}>
        <td>
          <label class="checkbox inline">
            ${stat.count }
          </label>
        </td>
        <td>
          <s:property value="jobGroup"/>.<s:property value="jobName"/>
        </td>
        <td>
          <s:property value="fireIp"/>
        </td>
        <td>
          <s:if test="status>3"><span class="badge badge-warning"><s:property value="jobState"/></span></s:if>
          <s:else><s:property value="jobState"/></s:else>
        </td>
        <td>
        <!-- s:date useage, Condition 3: With nice attribute as false and no format attribute is specified (file:///F:/org/Struts2.x/struts-2.2.1.1/docs/WW/date.html) -->
          <s:date name="new java.util.Date(fireTime)"/>
        </td>
        <td>
          <s:date name="new java.util.Date(endTime)"/>
        </td>
        <td>
          <s:date name="new java.util.Date(endTime-fireTime-28800000)" format="HH:mm:ss.SSS"/>
        </td>
        <td>
        	<s:property value="triggerInfo"/>
        </td>
        <td>
        	<s:property value="jobData"/>
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
///////////////////
</script>
<!-- // end trigger script -->



<script type="text/javascript">
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