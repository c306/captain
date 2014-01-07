<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>


<s:set value="%{getText('i18n.list.title')}" var="pagetitle"/>

<%@ include file="/jsp/admin/include/head.inc"%>
<%-- set vars --%>
<s:set var="editLink">${ctx}/acegi/role!edit.action?treeid=${param.treeid}</s:set>

<ul class="nav nav-tabs" id="myTab">
  <li class="active"><a href="#tab1" data-toggle="tab">Section 1</a></li>
</ul>
<div class="tab-content" style="padding-bottom: 9px; border-bottom: 1px solid #ddd;">
  <div class="tab-pane fade in active" id="tab1">

<%@ include file="/jsp/admin/include/alert.inc"%>

<s:form cssClass="form-search" id="appfrm" action="/acegi/role!list.action" method="post">
<!--  隐藏域 -->
  <input type="hidden" name=treeid value="${param.treeid}"/>
  <input type="hidden" name="keys" value=""/>
  <input type="hidden" name="oid" value=""/>
  <s:hidden name="sort"/>
  <s:hidden name="dir"/>
  
  <div id="search_div" class="well">
	<div class="row">
		<div class="control-group span4">
		  <label><s:text name="i18n.field.name"/>：</label>
		  <s:textfield cssClass="input-large" name="query.name" />
		  <!-- <input type="text" class="input-large" placeholder="" name="query.name" value=""> -->
		</div>
		<div class="control-group span4">
		  <label><s:text name="i18n.field.descn"/>：</label>
          <s:textfield cssClass="input-large" name="query.descn" />
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
        
        <th><s:text name="i18n.field.name"/></th>
        <th><s:text name="i18n.field.descn"/></th>
        <th> </th>
        
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
	          <s:property value="name"/>
	        </a>
        </td>
        <td>
	        <a href="<s:property value='#editLink'/>" onclick="doEdit('${item.id}','edit');return false;">
	          <s:property value="descn"/>
	        </a>
        </td>
        <td>
		      <a id="userbtn" class="btn" href="javascript:void(0);" onclick="showRoleUsers(${item.id})"><s:text name="assign.users"/></a>
		      <a id="rescbtn" class="btn" href="javascript:void(0);" onclick="showRoleResources(${item.id})"><s:text name="assign.resources"/></a>
        </td>
        
        
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


	<!-- dialog-role-select -->
	<div id="dialog-role-select" title="Select Dialog" style="padding-top:15px;"></div>
	<!-- dialog-role-select //end -->

</div><!-- dialog.div display:none; -->

<script type="text/javascript">
// dialogs init script
$(function($){
// initial code here

	  $( "#dialog-role-select" ).dialog({
	      modal: true,
	      autoOpen: false,
	      width: 800,
	      height: 560,
	      buttons: {
	    	  '<s:text name="global.button.label.save"/>': function() {
	            $( this ).dialog( "close" );
	            if( $( this ).attr('dialogType') == 'showUsers' ) {
	            	assignUsers();
	            } else {
	            	assignResources();
	            }
	          },
	          '<s:text name="global.button.label.cancel"/>' : function() {
	            $( this ).dialog( "close" );
	          }
	        }
	    });
	    
});


//dialog-message
function showDialogByRole(roleId, src) {
	
	//init
	currEditRoleId = roleId;
	assignedUids = [];
	assignedRids = [];
	
	var iframe = '<iframe src="' + src + '" style="width:100%; height:100%; border:0px;" frameborder="0" scrolling="auto"></iframe>';
	
	$( "#dialog-role-select" ).html("").append(iframe).dialog( "open" );
}
function showRoleUsers(roleId) {
	$( "#dialog-role-select" ).attr('dialogType', 'showUsers');
	var src = '${ctx}/acegi/user!listByRole?defaultResult=role_user_list&query.roleId='+roleId;
	showDialogByRole(roleId, src);
}
function showRoleResources(roleId) {
	$( "#dialog-role-select" ).attr('dialogType', 'showResources');
	var src = '${ctx}/acegi/resc!listByRole?defaultResult=role_resc_list&query.roleId='+roleId;
	showDialogByRole(roleId, src);
}

// select users start
var currEditRoleId='';
var assignedUids = [];// 选中的userids store {uid, op} op==-1:delete; op==1:add; op==0:do nothing;
var assignedRids = [];// 选中的resourceids store {rid, op} op==-1:delete; op==1:add; op==0:do nothing;
function selectUser(flag, uid) {
	var rtn = false;
	$.each(assignedUids, function(i,n){
		if( n.uid == uid ) {
			//{uid, op} op==-1:delete; op==1:add; op==0:do nothing;
			n.op = n.op + ( flag ? 1 : -1 );
			rtn = true;
			return false;
		}
	});
	if(rtn) {
		//如果数组中已经存在，立即返回
		return;
	}
	
	assignedUids[assignedUids.length] = { "uid": uid, "op": ( flag ? 1 : -1 ) };
}
//select resources end
function selectResource(flag, rid) {
	var rtn = false;
	$.each(assignedRids, function(i,n){
		if( n.rid == rid ) {
			//{rid, op} op==-1:delete; op==1:add; op==0:do nothing;
			n.op = n.op + ( flag ? 1 : -1 );
			rtn = true;
			return false;
		}
	});
	if(rtn) {
		//如果数组中已经存在，立即返回
		return;
	}
	
	assignedRids[assignedRids.length] = { "rid": rid, "op": ( flag ? 1 : -1 ) };
}
//select users end

//
function assignUsers() {
	
	var addUsers = []; removeUsers = [];
	
	$.each(assignedUids, function(i,n){
		//{uid, op} op==-1:delete; op==1:add; op==0:do nothing;
		if( n.op == 1 ) {
			//add
			addUsers[addUsers.length] = n.uid;
		} else if( n.op == -1 ) {
			//remove
			removeUsers[removeUsers.length] = n.uid;
		}
	});
	
	if(addUsers.length == 0 && removeUsers.length == 0) {
		// do nothing!
		return;
	}
	
	//
	var keys = "(" + addUsers.join(",") + ";" + addUsers.join(",") + ")";

	captainProgressbar(false);
	$.post(
			"${ctx}/acegi/role!assignUsers?randomnum="+new Date().getTime(),
			{keys: keys, "domain.id": currEditRoleId},
			function(data) {
				captainProgressbar(true);
		          //
		          if(data.success === true) {
		          	showMsg('<s:text name="global.hintinfo.savesuccess"/>', 1, 1);
		          } else
		          	showMsg('<s:text name="global.hintinfo.savefailed"/>', 1, 2);
			},
			"json"
	);
}
//
function assignResources() {
	
	var addResources = []; removeResources = [];
	
	$.each(assignedRids, function(i,n){
		//{rid, op} op==-1:delete; op==1:add; op==0:do nothing;
		if( n.op == 1 ) {
			//add
			addResources[addResources.length] = n.rid;
		} else if( n.op == -1 ) {
			//remove
			removeResources[removeResources.length] = n.rid;
		}
	});
	
	if(addResources.length == 0 && removeResources.length == 0) {
		// do nothing!
		return;
	}
	
	//
	var keys = "(" + addResources.join(",") + ";" + removeResources.join(",") + ")";

	captainProgressbar(false);
	$.post(
			"${ctx}/acegi/role!assignResources?randomnum="+new Date().getTime(),
			{keys: keys, "domain.id": currEditRoleId},
			function(data) {
				captainProgressbar(true);
		          //
		          if(data.success === true) {
		          	showMsg('<s:text name="global.hintinfo.savesuccess"/>', 1, 1);
		          } else
		          	showMsg('<s:text name="global.hintinfo.savefailed"/>', 1, 2);
			},
			"json"
	);
}



</script>
<!-- // end dialog -->



<script type="text/javascript">
//删除
function doDelete(){
	doBatchSubmit('${ctx}/acegi/role!delete.action', '<s:text name="global.hintinfo.deleteconfirm"/>');
}
//
function doEdit(oid,method){
	$('#appfrm :input[name="oid"]').val(oid);
	$('#appfrm')[0].action = '${ctx}/acegi/role!'+method+'.action';
	$('#appfrm').submit();
}
//
function doExport(){
	location = '${ctx}/acegi/role!exportData.action?'+$('#appfrm').serialize();
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