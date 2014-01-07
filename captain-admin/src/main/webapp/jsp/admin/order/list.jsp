<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.navi.headline.title.ordermanagement')}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

<form id="appfrm" action="${ctx}/admin/order!list.action" method="post">
<!--  隐藏域 -->
  <s:token />
	<input type="hidden" name="keys"/>
	<input type="hidden" name="oid"/>
	<input type="hidden" name="query.status" value="${query.status}"/>
	
	<div id="tab1" class="tab-content default-tab" style="display: block; border:0px solid #faa;">
	<div class="" style="text-align: right;">
	<table>
		<tr>
			<td width=80 align="right"><s:text name="admin.label.order.no"/>:</td>
			<td align="left">
				<input type="text" name="query.orderno" value="${query.orderno}" class="text-input">
			</td>
            <td width=80 align="right"><s:text name="admin.label.order.fullname"/>:</td>
            <td><input type="text" name="query.fullname" value="${query.fullname}" class="text-input"></td>
            <td>
            </td>
		</tr>
	</table>
    <a class="button" href="javascript:void(0);" onclick="$('#appfrm').submit();"><s:text name="global.button.label.search"/></a>
        &nbsp;&nbsp;
    <a class="button" href="javascript:void(0);" onclick="doExport();"><s:text name="global.button.label.export"/></a>
        &nbsp;&nbsp;
    <a class="button" href="javascript:void(0);" onclick="doDelete(2);"><s:text name="global.button.label.delete"/></a>
	<s:if test="query.status == 0">
	    &nbsp;&nbsp;
    <a class="button" href="javascript:void(0);" onclick="doConfirm();"><s:text name="admin.order.button.label.save1"/></a>
	</s:if>
	
	</div>
	
		<table class="cnttbl">
			
			<thead>
				<tr>
				   <th width="10"><input type="checkbox" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
				   <th nowrap="true"><s:text name="admin.label.comm.serial"/></th>
				   <th width="70"><s:text name="admin.thead.order.orderno"/></th>
				   <th><s:text name="admin.thead.order.customer"/></th>
				   <th width="88"><s:text name="admin.thead.order.amount"/></th>
				   <th nowrap="nowrap"><s:text name="admin.thead.order.status"/></th>
				   <th><s:text name="admin.label.comm.createtime"/></th>
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
					<td><input type="checkbox" name=_checkItem value="${item.orderno}"></td>
					<td align="center">${stat.count }</td>
					<td>
		<a href="${ctx }/admin/order!edit.action?oid=${item.orderno}" onclick="doEdit('${item.orderno}');return false;">
			<s:property value="orderno"/>
		</a>
					</td>
					<td align="center">
                        <s:property value="fullname"/>
					</td>
					<td align="right">
                        <s:property value="%{getText('global.format.intmoney',{amount})}"/>
					</td>
					<td align="center" style="${item.status == 0 ? "color:red;" : (item.status < 5 ? "color:green;":"")}">
                        <s:property value="%{iscn ? statusName : statusEname}"/>
					</td>
					<td align="center">
                        <s:date name="createtime"/>
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
			$('#appfrm')[0].action = '${ctx}/admin/order!move2trash.action';
		} else if(delstyle == 2){
			$('#appfrm')[0].action = '${ctx}/admin/order!delete.action';
		}
		$('#appfrm').submit();
	}
}
function doConfirm() {
	var ids = [];
	$(':checkbox[name=_checkItem][checked=true]').each(function(i){
		ids[i] = this.value;
	});
	if(ids.length == 0){
		alert('<s:text name="global.hintinfo.letselectdeleterecord"/>');
		return false;
	}
	if(confirm('<s:text name="global.hintinfo.submitconfirm.orders"/>')){
		$('#appfrm :input[name=keys]').val(ids.join(','));
		$('#appfrm')[0].action = '${ctx}/admin/order!orderConfirm.action';
		$('#appfrm').submit();
	}
}
//
function doEdit(oid){
	$('#appfrm :input[name="oid"]').val(oid);
	$('#appfrm')[0].action = '${ctx}/admin/order!edit.action';
	$('#appfrm').submit();
}
function doExport(){
	location = '${ctx}/admin/order!exportData.action?'+$('#appfrm').serialize();
}
jQuery.fn.extend({
	checkAll : function(checked) {
		return this.each(function() {
			this.checked = checked;
		});
	}
});
</script>

<%@ include file="/jsp/admin/include/foot.inc"%>