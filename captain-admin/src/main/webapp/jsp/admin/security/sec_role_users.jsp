<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<html>
<head>
<link rel="icon" type="image/ico" href="${ctx}/favicon.ico" />
<link rel="shortcut icon" href="${ctx}/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${pagetitle }, Welcome <sec:authentication property="name"/> !</title>


<style type="text/css">
body {
padding: 10px;
/*background-color: #efefef;*/
}
.asterisk_red {color:#FF0000 !important;font-weight:600;}
tr.selected {background-color: #efefef;}

/*table list style
.table tbody td { padding: 2px; vertical-align: bottom; }*/

</style>

<script src="${ctx}/lookfor/common/jquery/jquery-1.9.1.min.js" type="text/javascript" language="javascript"></script>

<!-- bootstrap styles -->
<link href="${ctx}/lookfor/common/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/lookfor/common/bootstrap/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="${ctx}/lookfor/common/bootstrap/js/bootstrap.js"></script>


<script type="text/javascript">

var ctx = '${ctx}';

$(document).ready(function(){
	<sec:authorize ifAnyGranted="ROLE_ADMIN,ROLE_OPERATOR">
	// do something ...
	</sec:authorize>
	
	
	$(':checkbox[name=_checkItem]').each(function(i,box){
		$.each(parent.assignedUids, function(i,n){
			if( n.uid == box.value ) {
				//{uid, op} op==-1:delete; op==1:add; op==0:do nothing;
				if( n.op == 1 ) {
					//add
					box.checked = true;
					$(box).parents('tr').addClass('selected');
				} else if( n.op == -1 ) {
					//remove
					box.checked = false;
					$(box).parents('tr').removeClass('selected');
				}
			}
		});
	});
	
});


function selectUser(flag, uid) {
	parent.selectUser(flag, uid);
}



</script>


</head>

<body style="padding-top: 10px;">

<form class="form-inline" action="${ctx}/acegi/user!listByRole">
<s:hidden name="defaultResult" />
<s:hidden name="query.roleId" />

<div class="well" style="padding:8px; margin-bottom:7px;">
  <input type="text" class="input-small" placeholder="Username" name="query.username" value="<s:property value="query.username" />">
  <input type="text" class="input-small" placeholder="Email" name="query.email" value="<s:property value="query.email" />">
  <label class="checkbox">
    <input type="checkbox"> Only show assigned
  </label>
  <button type="submit" class="btn">Search</button>
</div>

<div class="">
  <table class="table">
    <thead>
      <tr>
        <th>#<input type="checkbox" id="_checkAll" name="_checkAll" onclick="$(':checkbox[name=_checkItem]').checkAll(this.checked);" class="check-all"></th>
        <th><s:text name="i18n.field.username"/></th>
        <th><s:text name="i18n.field.firstName"/></th>
        <th><s:text name="i18n.field.lastName"/></th>
        <th><s:text name="i18n.field.email"/></th>
      </tr>
    </thead>
    <tbody>
    
    <!-- start page.datas -->
    <s:iterator var="item" value="page.datas" status="stat">
	 <tr ${( empty item.roleName ) ? '' : 'class="selected"'}>
        <td align="center">
          <label class="checkbox inline">
            <input type="checkbox" name=_checkItem value="${item.id}" ${( empty item.roleName ) ? '' : 'checked'} onclick="selectUser(this.checked, '${item.id}');$(this).parents('tr').toggleClass('selected');$('#_checkAll')[0].checked=false;"> ${stat.count }
          </label>
        </td>
        
        <s:set var="editLink"></s:set>
        <td>
	        <s:property value="username"/>
        </td>
        <td>
	        <s:property value="firstName"/>
        </td>
        <td>
	        <s:property value="lastName"/>
        </td>
        <td>
	        <s:property value="email"/>
        </td>
      </tr>
    </s:iterator> <!-- End list iterator -->
      
    </tbody>
  </table>
  
  
	<div class="pagination pagination-centered">
	  <s:property value="%{@com.c306.core.support.PagedUtil@bootstrapNavi(page,#attr.ctx,iscn)}" escapeHtml="false"/>
	</div> <!-- End .pagination -->
  
  
  
</div>

</form>

<script type="text/javascript">
//////////utils//////////////////////
jQuery.fn.extend({
	checkAll : function(checked) {
		return this.each(function() {
			if( checked && this.checked ){
				// if it has bean selected, do nothing
				return;
			}
			selectUser(checked, this.value);
			if(checked){
				$(this).parents('tr').addClass("selected");
			} else {
				$(this).parents('tr').removeClass("selected");
			}
			this.checked = checked;
		});
	}
});
</script>


</body>
</html>