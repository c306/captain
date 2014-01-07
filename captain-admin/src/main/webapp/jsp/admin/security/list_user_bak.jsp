<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.navi.headline.title.usermanagement')}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

<form id="appfrm" action="${ctx}/admin/user!list.action" method="post">
<!--  隐藏域 -->
  <s:token />
	<input type="hidden" name="keys"/>
	<input type="hidden" name="oid"/>
	
	<div id="tab1" class="tab-content default-tab" style="display: block; border:0px solid #faa;">
	<div class="" style="text-align: right;">
	<table>
		<tr>
			<td width=100 align="right"><s:text name="admin.label.userinfo.fullname"/>:</td>
			<td align="left">
				<input type="text" name="query.username" value="${query.username}" class="text-input">
			</td>
			<td width=100 align="right"><s:text name="admin.label.userinfo.email"/>:</td>
			<td align="left">
				<input type="text" name="query.email" value="${query.email}" class="text-input">
			</td>
		</tr>
		<tr>
			<td align="right"><s:text name="admin.thead.comm.datefrom"/>:</td>
			<td align="left">
				<s:textfield name="query.createtime" value="%{getText('global.format.date',{query.createtime})}"
                 cssClass="text-input" onfocus="WdatePicker({skin:'simple', dateFmt:'%{getText('global.format.pattern.date')}'})"/>
                 <img src="${ctx }/common/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
			</td>
			<td align="right"><s:text name="admin.thead.comm.dateto"/>:</td>
			<td align="left">
				<s:textfield name="query.updatetime" value="%{getText('global.format.date',{query.updatetime})}"
                 cssClass="text-input" onfocus="WdatePicker({skin:'simple', dateFmt:'%{getText('global.format.pattern.date')}'})"/>
                 <img src="${ctx }/common/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
			</td>
		</tr>
	</table>
		<a class="button" href="javascript:void(0);" onclick="$('#appfrm').submit();"><s:text name="global.button.label.search"/></a>
        &nbsp;&nbsp;
        <a class="button" href="javascript:void(0);" onclick="doExport();"><s:text name="global.button.label.export"/></a>
		&nbsp;&nbsp;&nbsp;&nbsp;
<%--	<a class="button" href="${ctx}/admin/user!edit.action" onclick=""><s:text name="global.button.label.add"/></a>	--%>
<%--		<a class="button" href="javascript:void(0);" onclick="doDelete(2);"><s:text name="global.button.label.delete"/></a>	--%>
	</div>
	
		<table class="cnttbl">
			
			<thead>
				<tr>
				   <th width="10"><input type="checkbox" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
				   <th nowrap="true"><s:text name="admin.label.comm.serial"/></th>
				   <th><s:text name="admin.label.userinfo.fullname"/></th>
				   <th><s:text name="admin.label.userinfo.city"/></th>
				   <th nowrap="nowrap"><s:text name="admin.label.userinfo.email"/></th>
                   <th><s:text name="admin.thead.comm.createtime"/></th>
				   <th> </th>
				</tr>
			</thead>
		 
			<tfoot>
				<tr>
					<td colspan="6">
						
						<div class="pagination">
							<s:property value="%{@com.c306.core.support.PagedUtil@greenNavi(page,#attr.ctx,iscn)}" escapeHtml="false"/>
						</div> <!-- End .pagination -->
						
					</td>
				</tr>
			</tfoot>
		 
			<tbody>
			<!-- start page.datas -->
			<s:iterator var="item" value="page.datas" status="stat">
			<%-- <tr class="<s:if test="#stat.even">even</s:if><s:else>odd</s:else>"> --%>
				<tr ${stat.even ? '' : 'class="alt-row"'}>
					<td><input type="checkbox" name=_checkItem value="${item.id}"></td>
					<td align="center">${stat.count }</td>
					<td align="center">
  		<a href="${ctx }/admin/user!edit.action?oid=${item.id}" onclick="doEdit('${item.id}');return false;">
                      <s:property value="username"/>
  		</a>
					</td>
					<td align="center">
			           <s:property value="city"/>
					</td>
					<td align="center">
					   <s:property value="email"/>
					</td>
					<td align="center">
					   <s:date name="createtime"/>
					</td>
					<td align="center" style="width:107px;">
	                   <a href="javascript:void(0);" onclick="userRoleSetting('${item.id}');return false;" title="User's Roles">
                        ROLE
                       </a>
                       |
	                   <a href="javascript:void(0);" onclick="shoppingHistory('${item.username}');return false;" title="Shopping History">
                        HISTORY
                       </a>
					</td>
				</tr>
				
			</s:iterator> <!-- End list iterator -->
			</tbody>
			
		</table>
		
	</div> <!-- End #tab1 -->

</form>


<script type="text/javascript">
function doDelete(delstyle){
	var ids = [];
	$(':checkbox[name=_checkItem][checked=true]').each(function(i){
		ids[i] = this.value;
	});
	if(ids.length == 0){
		alert('<s:text name="global.hintinfo.letselectdeleterecord"/>');
		return false;
	}
	if(confirm('<s:text name="global.hintinfo.deleteconfirm"/>')){
		$('#appfrm :input[name=keys]').val(ids.join(','));
		if(delstyle == 1){
			$('#appfrm')[0].action = '${ctx}/admin/user!move2trash.action';
		} else if(delstyle == 2){
			$('#appfrm')[0].action = '${ctx}/admin/user!delete.action';
		}
		$('#appfrm').submit();
	}
}
//
function doEdit(oid){
	$('#appfrm :input[name="oid"]').val(oid);
	$('#appfrm')[0].action = '${ctx}/admin/user!edit.action';
	$('#appfrm').submit();
}
function doExport(){
	location = '${ctx}/admin/user!exportData.action?'+$('#appfrm').serialize();
}
jQuery.fn.extend({
	checkAll : function(checked) {
		return this.each(function() {
			this.checked = checked;
		});
	}
});
</script>


<%-- start Shopping History--%>
<div id="dialog-shopping_history" title="Shopping History">
</div>
<script type="text/javascript">
$(document).ready(function(){
	
	$( "#dialog-shopping_history" ).dialog({
		autoOpen: false,
	    //height: $(document).height(),
	    height: 500,
	    width: 750,
		modal: true,
		close: function() {
			
		}
	});
	
});

function shoppingHistory(member) {
	var ifr = '<iframe runat="server" src="${ctx}/admin/order!shoppingHistory.action?query.fullname='+member+'" width="100%" height="450" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" allowtransparency="yes"></iframe>';
	$( "#dialog-shopping_history" ).html(ifr).dialog( "open" );
}
</script>
<%-- end Shopping History--%>

<%-- start 用户角色管理--%>
<s:action var="rolelist" name="secrole!ajaxList" namespace="/admin" ignoreContextParams="true">
  <s:param name="pageSize" value="1000"/>
</s:action>

<div id="dialog-userrole" title="User's Role Setting">
  <input type="hidden" name="userId"/>
  <s:iterator var="item" value="#attr.rolelist.page.datas" status="stat">
  <label style="width:20px;overflow:visible;" title="<s:property value="name"/>">
    <nobr><input type="checkbox" name="roleId" value="<s:property value="id"/>"/> <s:property value="descn"/></nobr>
  </label>
  </s:iterator>
</div>
<script type="text/javascript">
$(document).ready(function(){
	
	$( "#dialog-userrole" ).dialog({
		autoOpen: false,
		height: 300,
		width: 350,
		modal: true,
	  	buttons: {
	  		"Save": saveRoles,
	  		Cancel: function() {
	  			$( this ).dialog( "close" );
	  		}
	  	},
		close: function() {
			
		}
	});
	
});

function userRoleSetting(userid) {
	$( "#dialog-userrole" ).dialog( "open" );
	var $chks = $( "#dialog-userrole :checkbox" );
	$chks.attr('checked',false);
	$( "#dialog-userrole :hidden" ).val(userid);
	$.ajax({
		  url: "${ctx}/admin/secrole!retrieveRolesByUserId.action",
		  data: {'userId': userid},
		  cache: true,
		  async: true,
		  dataType: "json",
		  success: function(res){
			  if(res.success){
				  $.each(res.items, function(i, n){
				  		var chked = '';
				  		$.each($chks, function(j, c){
				  			if(c.value == n.id){
				  				c.checked = true;
				  				return;
				  			}
				  		});
				  	});
			  } else {
				  alert('Process Faild !');
			  }
		  },
		  error: function(){
			  alert('Error !');
		  }
		});
	
}
function saveRoles () {
	var dialog = this;
	var ids = [];
	$('#dialog-userrole :checkbox[name=roleId][checked=true]').each(function(i){
		ids[i] = this.value;
	});
  	$.ajax({
  	  url: "${ctx}/admin/user!saveRoles.action",
  	  data: {'domain.id': $( "#dialog-userrole :hidden[name=userId]" ).val(), 'keys': ids.join(',')},
  	  dataType: "json",
  	  success: function(res){
  		  $(dialog).dialog( "close" );
  		  if(res.success){
  			alert('Save Successfully !');
  		  } else {
  			alert('Generetor Error !');
  		  }
  	  },
	  error: function(){
  		  $(dialog).dialog( "close" );
		  alert('Error !');
	  }
  	});
}
</script>
<%-- end 用户角色管理--%>


<%@ include file="/jsp/admin/include/foot.inc"%>