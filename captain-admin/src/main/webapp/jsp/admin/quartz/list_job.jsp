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


<form id="appfrm" class="form-search" action="${ctx}/quartz/scheduler!jobList.action" method="post">
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
		  <label><s:text name="admin.label.quartz.job.group"/>：</label>
		  <input type="text" class="input-large" placeholder="" id="jobGroup" name="jobGroup" value="<s:property value="jobGroup.trim()"/>">
		</div>
		<div class="control-group span4">
		  <label><s:text name="admin.label.quartz.job.name"/>：</label>
          <input type="text" class="input-large" placeholder="" id="jobName" name="jobName" value="<s:property value="jobName.trim()"/>">
		</div>
	</div>
    <div class="row">
      <div class="pull-right">
        <a class="btn" href="javascript:void(0);" onclick="$('#appfrm').submit();"><s:text name="global.button.label.search"/></a>
        <!--<a class="btn" href="javascript:void(0);" onclick="doExport();"><s:text name="global.button.label.export"/></a>-->
        <a class="btn" href="javascript:void(0);" onclick="doTriggerJobs();"><s:text name="admin.label.quartz.job.triggerjobs"/></a>
        <a class="btn" href="javascript:void(0);" onclick="doPauseJobs();"><s:text name="admin.label.quartz.job.pausejobs"/></a>
        <a class="btn" href="javascript:void(0);" onclick="doResumeJobs();"><s:text name="admin.label.quartz.job.resumejobs"/></a>
        <a class="btn" href="${ctx}/quartz/scheduler!jobEdit.action?treeid=${param.treeid}" onclick=""><s:text name="global.button.label.add"/></a>
        <a class="btn" href="javascript:void(0);" onclick="doDeleteJobs(${param['query.status'] eq 0 ? 2 : 2});"><s:text name="global.button.label.delete"/></a>
      </div>
    </div>
  </div>
  
  <table class="table table-striped">
    <thead>
      <tr>
        <th width="30"><input type="checkbox" id="_checkAll" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
        <th><s:text name="admin.label.quartz.job.group"/>.<s:text name="admin.label.quartz.job.name"/></th>
        <th><s:text name="admin.label.quartz.job.description"/></th>
        <th width="120"><s:text name="admin.label.quartz.job.jobclassname"/></th>
      </tr>
    </thead>
    
    <tbody>
    <!-- start page.datas -->
    <s:iterator var="item" value="#request.jobDetailList" status="stat">
	 <tr ${stat.even ? '' : 'class=""'}>
        <td align="center">
          <label class="checkbox inline">
            <input type="checkbox" name=_checkItem value="${item.group}/${item.name}" onclick="$('#_checkAll').checkAll(false);"> ${stat.count }
          </label>
        </td>
        <td align="center" id="G_<s:property value="group"/>_<s:property value="name"/>">
              <a href="${ctx}/quartz/scheduler!jobEdit.action?jobGroup=<s:property value="group"/>&jobName=<s:property value="name"/>&treeid=${param.treeid}">
                <s:property value="group"/>.<s:property value="name"/>
              </a>
              <s:if test="jobDataMap.size() > 0">[<s:property value="@com.c306.core.quartz.JobInfoSupport@getJobDataForString(jobDataMap, ', ')"/>]</s:if>
              <br />
              <!-- trigger加号+ -->
              <span class="label" title="<s:property value="#request.triggersMap[#item.getKey()].size()"/>">triggers</span><i class="icon-plus" onclick="showTriggerDialog('<s:property value="group"/>','<s:property value="name"/>',null,null,true)"></i>:
              <!-- trigger list -->
              <s:iterator var="triggerDetail" value="#request.triggersMap[#item.getKey()]" status="trig_stat">
                <div class="trgr_li <s:property value="#request.pausedTrigSet.contains(key) ? 'trgr_li_warn' : ''"/>">
                  <span>[<s:property value="#request.pausedTrigSet.contains(key) ? 'paused' : 'actived'"/>]</span>
                  <a onclick="showTriggerDialog('<s:property value="jobGroup"/>','<s:property value="jobName"/>',
                                      '<s:property value="group"/>','<s:property value="name"/>',<s:property value="%{repeatInterval>0}"/>)" href="javascript:void(0);">
                  <s:property value="cronExpression"/>
                  <s:if test="cronExpression == null">
                  interval:<s:property value="repeatInterval / 1000"/>, count:<s:property value="repeatCount"/>
                  </s:if>
                  </a>
                  <i class="icon-minus" onclick="deleteTrigger(this,'<s:property value="group"/>','<s:property value="name"/>')"></i>
                  <span id="k_<s:property value="group"/>_<s:property value="name"/>" style="display:none;"><s:property value="#request.triggersJsonMap[#triggerDetail.getKey()]" escapeHtml="false" /></span>
                </div>
              </s:iterator>
        </td>
        <td align="center">
              <s:property value="description"/>
        </td>
        <td align="center">
            	<s:property value="jobClass.name"/>
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
#dialog_trigger_form .control-group { margin-bottom:2px; }
tr i.icon-plus, tr i.icon-minus {display:none;}
tr:hover i.icon-plus, tr:hover i.icon-minus {display:inline-block;}
/*trigger list style*/
.table tbody td { line-height: 22px; }
.table td div.trgr_li { font-size: 12px; }
.table td div.trgr_li_warn { color:#FF0000; }
</style>
<!-- dialog-trigger -->
<div id="dialog-trigger" title="<s:text name="admin.label.quartz.trigger.scheduleatrigger"/>" style="padding-top:15px;">
  <form id="dialog_trigger_form" name="tri_frm" class="form-horizontal">
      <input type="hidden" id="tri_frm_trigger_jobGroup" name="triggerInfo.jobGroup" value=""/>
      <input type="hidden" id="tri_frm_trigger_jobName" name="triggerInfo.jobName" value=""/>
      <input type="hidden" id="tri_frm_trigger_update" name="triggerInfo.update" value="0"/>
      <input type="hidden" id="tri_frm_trigger_triggerType" name="triggerInfo.triggerType" value=""/>
      <div class="control-group">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.type"/></label>
        <div class="controls">
          <label class="radio inline">
            <input type="radio" name="tri_frm_trigger_type" id="tri_frm_trigger_type_S" onclick="toggleTriggerFields(this.checked)" value="Simple Trigger"> Simple Trigger
          </label>
          <label class="radio inline">
            <input type="radio" name="tri_frm_trigger_type" id="tri_frm_trigger_type_C" onclick="toggleTriggerFields(!this.checked)" value="Cron Trigger"> Cron Trigger
          </label>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.name"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.name" id="tri_frm_trigger_name" class="input-xlarge"><span class="help-inline asterisk_red">*</span>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.group"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.group" value="DEFAULT" id="tri_frm_trigger_group" class="input-xlarge"><span class="help-inline asterisk_red">*</span>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.description"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.description" id="tri_frm_trigger_description" class="input-xlarge">
        </div>
      </div>
      <div class="control-group">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.calendarname"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.calendarName" id="tri_frm_trigger_calendarname" class="input-xlarge">
        </div>
      </div>
      <div class="control-group">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.priority"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.priority" id="tri_frm_trigger_priority" class="input-mini">
          <span class="help-inline"><s:text name="admin.label.quartz.trigger.prioritydesc"/></span>
        </div>
      </div>
      <div class="control-group">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.starttime"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.startTime" id="tri_frm_trigger_starttime" class="input-medium">
        </div>
      </div>
      <div class="control-group">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.endtime"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.endTime" id="tri_frm_trigger_endtime" class="input-medium">
        </div>
      </div>
      <div class="control-group" category="simple">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.repeatcount"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.repeatCount" id="tri_frm_trigger_repeatcount" class="input-small">
          <span class="help-inline"><s:text name="admin.label.quartz.trigger.indefinitely"/></span>
        </div>
      </div>
      <div class="control-group" category="simple">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.repeatinterval"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.repeatInterval" id="tri_frm_trigger_repeatinterval" class="input-small">
          <span class="help-inline asterisk_red">*</span>
        </div>
      </div>
      <div class="control-group" category="cron">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.cronexpression"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.cronExpression" id="tri_frm_trigger_cronexpression" class="input-xlarge">
          <span class="help-inline asterisk_red">*</span>
        </div>
      </div>
      <div class="control-group" category="cron">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.timezone"/></label>
        <div class="controls">
          <input type="text" name="triggerInfo.timeZone" value="GMT+08:00" id="tri_frm_trigger_timezone" class="input-xlarge">
        </div>
      </div>
      <div class="control-group">
        <label class="control-label"><s:text name="admin.label.quartz.trigger.triggerdata"/></label>
        <div class="controls">
          <textarea class="input-xlarge" id="jobInfo_paramDatas" name="triggerInfo.paramDatas" rows="2"></textarea>
        </div>
      </div>
  </form>
</div><!-- dialog-trigger //end -->
<!-- dialog-message -->
<div id="dialog-message" title="Information" style="padding-top:15px;">
  <div id="dialog-message-cnt"></div>
</div><!-- dialog-message //end -->
</div><!-- dialog.div display:none; -->
<script type="text/javascript">
// dialogs init script
$(function($){
  $( "#dialog-trigger" ).dialog({
      modal: true,
      autoOpen: false,
      width: 585,
      minHeight: 300,
      maxHeight: 600,
      buttons: {
        <s:text name="global.button.label.apply"/>: function() {
      	  if(!validateTriggerForm())
      		  return;
          postTriggerInfo();
        },
        <s:text name="global.button.label.cancel"/>: function() {
          $( this ).dialog( "close" );
        }
      }
    });
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
// dialog handler
function showTriggerDialog(jobGroup,jobName,trigGroup,trigName,isSimpleTrigger) {
	// initialize form fields
	$('#dialog_trigger_form').get(0).reset();
	$("#dialog_trigger_form").find(':text').each(function(idx,el){
		$(el).parents('div.control-group').removeClass('error');
	});
	$('#tri_frm_trigger_jobGroup').val(jobGroup);
	$('#tri_frm_trigger_jobName').val(jobName);
	if( trigGroup!=null && trigName!=null ) { // means update trigger
		$('#tri_frm_trigger_update').val(1);
		$('#tri_frm_trigger_group').addClass('uneditable-input').get(0).disabled = true;
		$('#tri_frm_trigger_name').addClass('uneditable-input').get(0).disabled = true;
		// load triggger info and fill the fields
		var jsonObj = $('#dialog_trigger_form').data(trigGroup+'_'+trigName);
		if(!jsonObj) {
			jsonObj = jQuery.parseJSON($('#k_'+trigGroup+'_'+trigName).text());
			$('#dialog_trigger_form').data(trigGroup+'_'+trigName, jsonObj);
		}
		// fill form input:text
		$("#dialog_trigger_form").find(':text').each(function(idx,el){
			if(jsonObj[el.name.substring(12)]!==undefined) el.value = jsonObj[el.name.substring(12)];
		});
	} else { // means schedule a new trigger
		$('#tri_frm_trigger_group').removeClass('uneditable-input').get(0).disabled = false;
		$('#tri_frm_trigger_name').removeClass('uneditable-input').get(0).disabled = false;
	}
	//show/hide form fields
	toggleTriggerFields( isSimpleTrigger );
	// open dialog
	$( '#dialog-trigger' ).dialog( 'open' );
}
/**
 * show/hide form fields
 */
function toggleTriggerFields(isSimpletrigger) {
	// toggel simple/cron
	var $fieldDivs = $('#dialog_trigger_form').children('.control-group');
	if( isSimpletrigger ) {
		// simple
		$fieldDivs.filter('[category="simple"]').show();
		$fieldDivs.filter('[category="cron"]').hide();
		$('#tri_frm_trigger_triggerType').val('SIMPLE');
		$('#tri_frm_trigger_type_C').get(0).checked = false;
		$('#tri_frm_trigger_type_S').get(0).checked = true;
    } else {// cron
		$fieldDivs.filter('[category="simple"]').hide();
		$fieldDivs.filter('[category="cron"]').show();
		$('#tri_frm_trigger_triggerType').val('CRON');
		$('#tri_frm_trigger_type_C').get(0).checked = true;
		$('#tri_frm_trigger_type_S').get(0).checked = false;
    }
}
///// submit trigger info to server
function postTriggerInfo() {
	var params = {};
	$("#dialog_trigger_form").find(':input').each(function(idx,el){
		params[el.name] = el.value;
	});
	//alert($.param(params));
	showProgressbar(false);
	$.post("${ctx}/quartz/scheduler!schedulerTrigger.action",
		params,
		function(data){
		  showProgressbar(true);
          //
          if(data.success === true) {
          	showMsg("Successfully.", 1, 1);
            $( '#dialog-trigger' ).dialog( 'close' );
            var tInfo = data.jsonMap.triggerInfo;
            $('#dialog_trigger_form').data(tInfo.group+'_'+tInfo.name, tInfo);
            var txt = tInfo.triggerType=='CRON' ? tInfo.cronExpression : 'interval:'+tInfo.repeatInterval+', count:'+tInfo.repeatCount;;
            if( params['triggerInfo.update'] == 1 ) {
            	var $div = $( '#k_'+tInfo.group+'_'+tInfo.name ).parent();
            	$div.removeClass('trgr_li_warn').children('span:first').text('[actived]');
            	$div.find('a').text(txt);
            } else {
                var html = '<div><span>[actived]</span> <a href="javascript:void(0)">'+ txt +'</a><i class="icon-minus"></i></div>';
                $( '#G_'+tInfo.jobGroup+'_'+tInfo.jobName ).append(html);
            }
          } else
          	showMsg(data.jsonMap.msg, 1, 2);
		}, "json");
}
//init validate trigger form field
$('#tri_frm_trigger_group, #tri_frm_trigger_name, #tri_frm_trigger_repeatinterval, #tri_frm_trigger_cronexpression').blur(function(event){
	
	if($(this).val().trim() == '') {
		$(this).parents('div.control-group').addClass('error');
	} else {
		$(this).parents('div.control-group').removeClass('error');
	}
	
});
function validateTriggerForm() {
	var pass = true;
	var validFun = function(selectors){
		$(selectors).each(function(idx, el){
			if($(this).val().trim() == '') {
				pass = false;
				$(this).parents('div.control-group').addClass('error');
			}
		});
	}
	validFun('#tri_frm_trigger_group, #tri_frm_trigger_name', true);
	// if simple checked
	if( $('#tri_frm_trigger_type_S').get(0).checked ){
		validFun('#tri_frm_trigger_repeatinterval');
	}
	// if cron checked
	if( $('#tri_frm_trigger_type_C').get(0).checked ){
		validFun('#tri_frm_trigger_cronexpression');
	}
	return pass;
}
///////////////////
// delete trigger
function deleteTrigger(src,trigGroup,trigName) {
	if(!confirm('<s:text name="global.hintinfo.operateconfirm"/>')) return;
	var $thisDiv = $(src).parent('div');
	showProgressbar(false);
	$.post("${ctx}/quartz/scheduler!deleteTriggers.action",
		{triggerGroup: trigGroup, triggerName: trigName},
		function(data){
		  showProgressbar(true);
          //
          if(data.success === true) {
          	//showMsg("Successfully.", 1, 1);
        	  $thisDiv.hide();
          } else
          	showMsg(data.jsonMap.msg, 1, 2);
		}, "json");
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
		$('#appfrm :input[name=keys]').val(ids.join(','));
		$('#appfrm')[0].action = actionUrl;
		$('#appfrm').submit();
	}
}
// Execute immediately
function doTriggerJobs(){
	doBatchSubmit('${ctx}/quartz/scheduler!triggerJobs.action', '<s:text name="admin.label.quartz.job.triggerJobs.hitinfo"/>');
}
//暂停Job
function doPauseJobs(){
	doBatchSubmit('${ctx}/quartz/scheduler!pauseJobs.action', '<s:text name="admin.label.quartz.job.pausejobs.hitinfo"/>');
}
//恢复启用Job
function doResumeJobs(delstyle){
	doBatchSubmit('${ctx}/quartz/scheduler!resumeJobs.action', '<s:text name="admin.label.quartz.job.resumejobs.hitinfo"/>');
}
//删除Job
function doDeleteJobs(){
	doBatchSubmit('${ctx}/quartz/scheduler!deleteJobs.action', '<s:text name="global.hintinfo.deleteconfirm"/>');
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