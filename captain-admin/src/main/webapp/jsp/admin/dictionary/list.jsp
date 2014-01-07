<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.navi.headline.title.dicmanagement')}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

<form id="appfrm" action="${ctx}/admin/dict!list.action" method="post">
<!--  隐藏域 -->
  <s:token />
	<input type="hidden" name="keys"/>
	<s:hidden name="oid"/>
	<s:hidden name="domain.status"/>
	
	
	<div id="tab1" class="tab-content default-tab" style="display: block; border:0px solid #faa;">
	<div class="" style="text-align: right;">
	<table>
		<tr>
			<td width=100><s:text name="admin.thead.comm.key"/>:</td>
			<td><input type="text" name="query.key" value="${query.key}" class="text-input"></td>
			<td width=100><s:text name="admin.thead.comm.english.title"/>:</td>
			<td><input type="text" name="query.ename" value="${query.ename}" class="text-input"></td>
			<td width=100><s:text name="admin.thead.comm.chinese.title"/>:</td>
			<td align="left">
				<input type="text" name="query.name" value="${query.name}" class="text-input">
			</td>
		</tr>
	</table>
		<a class="button" href="javascript:void(0);" onclick="$('#appfrm').submit();"><s:text name="global.button.label.search"/></a>
		<a class="button" href="${ctx}/admin/dict!edit.action" onclick=""><s:text name="global.button.label.add"/></a>
		<a class="button" href="javascript:void(0);" onclick="doDelete(2);"><s:text name="global.button.label.delete"/></a>
	</div>
	
		<table class="cnttbl">
			
			<thead>
				<tr>
				   <th width="10"><input type="checkbox" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
				   <th nowrap="true"><s:text name="admin.label.comm.serial"/></th>
				   <th><s:text name="admin.thead.comm.chinese.title"/></th>
				   <th><s:text name="admin.thead.comm.english.title"/></th>
				   <th><s:text name="admin.thead.comm.lastmodifytime"/></th>
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
					<td><input type="checkbox" name=_checkItem value="${item.oid}"></td>
					<td>${stat.count }</td>
					<td>
		<a href="${ctx }/admin/dict!edit.action?oid=${item.oid}" onclick="doEdit('${item.oid}');return false;">
			<s:property value="name"/>
		</a>
					</td>
					<td>
		<a href="${ctx }/admin/dict!edit.action?oid=${item.oid}" onclick="doEdit('${item.oid}');return false;">
			<s:property value="ename"/>
		</a>
					</td>
					<td>
						<s:date name="updatetime"/>
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
			$('#appfrm')[0].action = '${ctx}/admin/dict!move2trash.action';
		} else if(delstyle == 2){
			$('#appfrm')[0].action = '${ctx}/admin/dict!delete.action';
		}
		$('#appfrm').submit();
	}
}
//
function doEdit(oid){
	$('#appfrm :input[name="oid"]').val(oid);
	$('#appfrm')[0].action = '${ctx}/admin/dict!edit.action';
	$('#appfrm').submit();
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