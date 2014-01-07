<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.navi.headline.title.sendemail')}" var="pagetitle"/>
<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<%--如果是普通用户转向
<sec:authorize ifAnyGranted="ROLE_MEMBER">
  <c:redirect url="/admin/secuser!userinfo.action" context="${ctx }" />
</sec:authorize>--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${pagetitle }</title>

<link href="${ctx}/lookfor/common/jquery/jquery-ui/css/ui-lightness/jquery-ui-1.10.2.custom.min.css" rel="stylesheet" type="text/css" />

<script src="${ctx}/lookfor/common/jquery/jquery-1.9.1.min.js" type="text/javascript" language="javascript"></script>
<script src="${ctx}/lookfor/common/jquery/jquery-ui/jquery-ui-1.10.2.custom.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript">
function MM_goToURL() {var i, args=MM_goToURL.arguments; document.MM_returnValue = false;for (i=0; i<(args.length-1); i+=2) eval(args[i]+".location='"+args[i+1]+"'");}
</script>
<script type="text/javascript" src="${ctx}/lookfor/common/ckeditor/ckeditor.js"></script>
<style>
    body { font-size: 62.5%; }
    label, input { display:block; }
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
	div.dict-contain { width: 700px;; margin: 20px 0; }
	div.dict-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
	div.dict-contain table td, div.dict-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
	.ui-dialog .ui-state-error { padding: .3em; }
	.validateTips { border: 1px solid transparent; padding: 0.3em; }
    div.dict-contain table td input {width:100%;}
</style>

</head>

<body>

<!-- content -->
<div class="content01" id="main-content">

          
<div id="color-contain" class="dict-contain ui-widget">
	<h1>Lastest Shopping History </h1>
	<table class="ui-widget ui-widget-content">
		<thead>
			<tr class="ui-widget-header ">
				   <th nowrap="true"><s:text name="admin.label.comm.serial"/></th>
				   <th width="70"><s:text name="admin.thead.order.orderno"/></th>
				   <th><s:text name="admin.thead.order.customer"/></th>
				   <th width="88"><s:text name="admin.thead.order.amount"/></th>
				   <th nowrap="nowrap"><s:text name="admin.thead.order.status"/></th>
				   <th><s:text name="admin.label.comm.createtime"/></th>
				   <th><s:text name="admin.label.operate" /></th>
			</tr>
		</thead>
    
    
			<tbody>
			<!-- start page.datas -->
			<s:iterator var="item" value="page.datas" status="stat">
			<%-- <tr class="<s:if test="#stat.even">even</s:if><s:else>odd</s:else>"> --%>
				<tr ${stat.even ? '' : 'class="alt-row"'}>
					<td>${stat.count }</td>
					<td>
		<a href="${ctx }/admin/order!edit.action?defaultResult=history_view&oid=${item.orderno}">
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
					<td align="center">
						<s:if test="#item.paystatus != 1">
							<a class="button" href="javascript:void(0);" onclick="MM_goToURL('parent','${ctx}/query/page!continuePay.action?oid=${item.orderno}')">
								<s:text name="main.mycart.jsp.proceed.payment" />
							</a>
						</s:if>
					</td>
				</tr>
				
			</s:iterator> <!-- End list iterator -->
			</tbody>
      
      
	</table>
</div>


<p></p>
<p></p>

<div id="dialog-form" title="Create">
  
</div>


<script type="text/javascript">


$(document).ready(function(){
	
	$(".Button002").button();//.css({'margin-left':2, height:35}).find('span').css({'text-decoration': 'underline'});
	
	var name = $( "#domain_name" ),
		ename = $( "#domain_ename" ),
		allFields = $( [] ).add( name ).add( ename ).add( $('#domain_key') );
		
	$( "#dialog-form" ).dialog({
		autoOpen: false,
		height: 230,
		width: 350,
		modal: true,
		buttons: {
			Cancel: function() {
				$( this ).dialog( "close" );
			}
		},
		close: function() {
			allFields.val( "" ).removeClass( "ui-state-error" );
		}
	});
	$( "#addColorbtn" ).click(function() {
		$('#domain_key').val('anyshopstyle_clothes_color');
		$( "#dialog-form" ).dialog( "open" );
	});
	
	
});

function deleDict(oid){
	if(!confirm('Are you sure ?'))
		return false;
	$.ajax({
		  url: "${ctx}/admin/dict!ajaxDelete.action",
		  data: {'oids': oid},
		  cache: true,
		  async: true,
		  dataType: "json",
		  type: 'POST',
		  success: function(res){
			  if(res.success){
				  alert('Successfully !');
			  } else {
				  alert('Error !');
			  }
			  location.reload();
		  }
		});
}
</script>

</div>
</body>
</html>
