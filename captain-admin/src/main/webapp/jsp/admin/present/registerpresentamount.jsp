<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.navi.headline.title.signupbonusmanagement')}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

<s:form action="/admin/signupbonus!saveOrUpdate.action" method="post" id="appfrm">

<!-- 	隐藏域 -->
  <s:token />
  <s:hidden name="domain.oid"/>
  <s:hidden name="domain.status"/>
  <s:hidden name="defaultResult" value="edit"/>
  
  <div id="tab2" class="tab-content" style="display: block;">

      <fieldset>
        <!-- Set class to "column-left" or "column-right" on fieldsets to divide the form into columns -->

        <p>
          <label class="column-left04">
            <s:text name="admin.label.present.current.signupbonus"/>:
          </label>
          <input type="text" id="domain_old_amount" value="${domain.amount}" disabled="disabled" class="text-input small-input readonly"/>
          <%--<span class="input-notification success png_bg">Successful message</span>--%>
          <%--<span class="input-notification error png_bg">${fieldErrors['domain.username'][0]}</span>--%>
          <%--<br><small><s:fielderror fieldName="domain.etitle" /></small>--%>
        </p>
        <p>
          <label class="column-left04">
            <s:text name="admin.label.present.new.signupbonus"/>:
          </label>
          <s:textfield name="domain.amount" value="" cssClass="text-input small-input"/>
          <%--<span class="input-notification success png_bg">Successful message</span>--%>
          <%--<span class="input-notification error png_bg">${fieldErrors['domain.username'][0]}</span>--%>
          <%--<br><small><s:fielderror fieldName="domain.etitle" /></small>--%>
        </p>
        
        <p/>
        <p/>
        
        <div class="managerCell002">
        <p>
          <label class="column-left04"> </label>
          <sec:authorize ifAnyGranted="ROLE_ADMIN">
          <span class="Button002" id="savebtn">
            <s:text name="global.button.label.update"/>
          </span>
          </sec:authorize>
        </p>
        </div>

      </fieldset>

      <div class="clear"></div>
      <!-- End .clear -->

  </div>

</s:form>

<script type="text/javascript">
function doConfirm(){
  if($("#appfrm_domain_amount").val() == ''){
    $("#appfrm_domain_amount").show("highlight").show("highlight").show("highlight").focus();
    return;
  }
  $("#tab2 .Button002").button("disable");
  $('#appfrm').submit();
}


jQuery(function($) {
	$("#domain_first").focus();
	
	$("#tab2 .Button002").button().css({'margin-left':2, height:35}).find('span').css({'text-decoration': 'underline'});
  	$('#savebtn').click(function(event){
		doConfirm();
	});
});
</script>


<%@ include file="/jsp/admin/include/foot.inc"%>