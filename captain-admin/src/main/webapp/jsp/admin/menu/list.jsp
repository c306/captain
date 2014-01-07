<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('main.head.inc.meun')}" var="pagetitle"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

<s:form id="appfrm" action="/admin/menu!update.action" method="post">


<div id="tab2" class="tab-content">


      <fieldset>
        <s:iterator var="item" value="page.datas" status="st">
        	<p>
        		<input type="hidden" name="domain.menu[${st.index}].oid" value="${item.oid}"/>
        		<input type="hidden" name="domain.menu[${st.index}].menuid" value="${item.menuid}"/>
        		
        		<s:text name="admin.label.dic.name"/>:
        		<input type="text" name="domain.menu[${st.index}].name" value="${item.name}" class="text-input" style="width: 100px;">&nbsp;&nbsp;
        		
        		<s:text name="admin.label.dic.ename"/>:
        		<input type="text" name="domain.menu[${st.index}].ename" value="${item.ename}" class="text-input" style="width: 100px;">&nbsp;&nbsp;
        		
        		<s:text name="admin.label.designer.derivation.isshow"/>:
        		<input type="checkbox" name="domain.menu[${st.index}].status" ${item.status==1?'checked="checked"':'' } value="1">&nbsp;&nbsp;
        		
        		<s:text name="admin.thead.comm.serial"/>:
        		<input type="text" name="domain.menu[${st.index}].ordinal" value="${item.ordinal}" class="text-input" style="width: 40px;">
        		
        	</p>
        </s:iterator>
        
        <div class="managerCell002">
		<sec:authorize ifAnyGranted="ROLE_ADMIN">
		</sec:authorize>
          <span class="Button002" id="savebtn">
            <s:text name="global.button.label.save"/>
          </span>
        </div>

      </fieldset>

      <div class="clear"></div>
      <!-- End .clear -->

  </div>
  
</s:form>

<p></p>
<p></p>
<script type="text/javascript">

$(document).ready(function(){
	$("#tab2 .Button002").button().css({'margin-left':2, height:35}).find('span').css({'text-decoration': 'underline'});
	
	$('#savebtn').click(function(event){
		$("#tab2 .Button002").button("disable");
		$('#appfrm')[0].submit();
	});

});
</script>
<%@ include file="/jsp/admin/include/foot.inc"%>