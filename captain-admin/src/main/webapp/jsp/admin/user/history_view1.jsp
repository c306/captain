<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{'history_view'}" var="pagetitle"/>
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

<script type="text/javascript" src="${ctx}/lookfor/common/ckeditor/ckeditor.js"></script>
<style>
    body { font-size: 12px; }
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

<style>
#tab2 fieldset p {
  height: 16px;
  margin-top:1px;
  margin-bottom:0px;
  padding-top:5px;
  padding-bottom:0px;
}
#tab2 p label {
  padding-top:1px;
  padding-bottom:0px;
  height: 9px;
  text-align: right;
}

table.cnttbl thead th {
  font-size: 11px !important;
  height:20px;
  padding-top: 1px;
  padding-bottom: 1px;
}
table.cnttbl td {
  height:20px;
  padding-top: 1px;
  padding-bottom: 1px;
}
</style>

<s:form id="appfrm" action="/mi/order!orderProcess.action" method="post" enctype="multipart/form-data">
<!-- 查询条件 -->
    <s:hidden name="pageNo"/>
    <s:hidden name="pageSize"/>
    <s:hidden name="sort"/>
    <s:hidden name="dir"/>
    <s:hidden name="query.key"/>
    <s:hidden name="query.ename"/>
    <s:hidden name="query.name"/>
<!-- 	隐藏域 -->
	<s:token />
	<s:hidden name="domain.orderno"/>
	<s:hidden name="domain.status"/>
	<s:hidden name="domain.createtime"/>
    
<div id="tab2" class="tab-content">


      <fieldset>

        <p>
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.no"/>:
          </label>
          &nbsp;
          ${domain.orderno }
        </p>
        <p>
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.orderstatus"/>:
          </label>
          &nbsp;
          ${iscn ? domain.statusName : domain.statusEname }
        </p>
        <p>
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.fullname"/>:
          </label>
          &nbsp;
          ${domain.fullname }
        </p>
        <p>
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.hometel"/>:
          </label>
          &nbsp;
          ${domain.hometel }
        </p>
        <p>
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.mobiletel"/>:
          </label>
          &nbsp;
          ${domain.mobiletel }
        </p>
        <p>
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.email"/>:
          </label>
          &nbsp;
          ${domain.email }
        </p>
        <p>
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.city"/>:
          </label>
          &nbsp;
          ${domain.city }
        </p>
        <p>
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.address"/>:
          </label>
          &nbsp;
          ${domain.address }
        </p>
        <p style="display:none;">
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.coupon"/>:
          </label>
          &nbsp;
          ${domain.coupno }
        </p>
        <p>
          <label style="width:160px;float:left;">
            <s:text name="main.checkout.jsp.send.time"/>:
          </label>
          &nbsp;
          <s:if test="domain.deliverytime == 1">
          	<s:text name="main.checkout.jsp.every.time" />
          </s:if>
          <s:elseif test="domain.deliverytime == 2">
          	<s:text name="main.checkout.jsp.work.time" />
          </s:elseif>
          <s:elseif test="domain.deliverytime == 3">
          	<s:text name="main.checkout.jsp.holiday.time" />
          </s:elseif>
        </p>
        <s:if test="domain.invoice == 1">
        	<p>
	          <label style="width:160px;float:left;">
	            <s:text name="main.checkout.jsp.invoice.type"/>:
	          </label>
	          &nbsp;
	          ${domain.invoicetype}%
	        </p>
	        <p>
	          <label style="width:160px;float:left;">
	            <s:text name="main.checkout.jsp.invoice.content"/>:
	          </label>
	          &nbsp;
	          <s:if test="domain.invoicecontent == 1">
	          	<s:text name="main.checkout.jsp.invoice.content.clothes" />
	          </s:if>
	          <s:elseif test="domain.invoicecontent == 2">
	          	<s:text name="main.checkout.jsp.invoice.shoes.hats" />
	          </s:elseif>
	          <s:elseif test="domain.invoicecontent == 3">
	          	<s:text name="main.checkout.jsp.invoice.articles.use" />
	          </s:elseif>
	          <s:elseif test="domain.invoicecontent == 4">
	          	<s:text name="main.checkout.jsp.invoice.office.goods" />
	          </s:elseif>
	        </p>
	        <p>
	          <label style="width:160px;float:left;">
	            <s:text name="main.checkout.jsp.invoice.title"/>:
	          </label>
	          &nbsp;
	          ${domain.invoicetitle}
	        </p>
        </s:if>
        <p>
          <label style="width:160px;float:left;">
            <s:text name="main.checkout.jsp.order.message"/>:
          </label>
          &nbsp;
          ${domain.ordermsg}
        </p>
        <p style="color: red;">
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.paytype"/>:
          </label>
          &nbsp;
          <s:if test="domain.paytype == 2">
          	<s:text name="main.checkout.jsp.paytype.chinabank" />
          </s:if>
          <s:elseif test="domain.paytype == 3">
          	<s:text name="main.checkout.jsp.paytype.paypal" />
          </s:elseif>
          <s:elseif test="domain.paytype == 1">
          	<s:text name="main.checkout.jsp.paytype.alipay" />
          </s:elseif>
          <s:elseif test="domain.paytype == 4">
          	<s:text name="main.checkout.jsp.paytype.chinapay" />
          </s:elseif>
          <s:elseif test="domain.paytype == 5">
          	<s:text name="main.checkout.jsp.paytype.cash" />
          </s:elseif>
        </p>
        <s:if test="domain.paytype != 5">
	        <p style="color: red;">
	          <label style="width:160px;float:left;">
	            <s:text name="admin.label.order.paystatus"/>:
	          </label>
	          &nbsp;
	          <s:if test="domain.paystatus == 1">
	          	<s:text name="main.checkout.jsp.paystatus.succ" />
	          </s:if>
	          <s:elseif test="domain.paystatus == 2">
	          	<s:text name="main.checkout.jsp.paystatus.pending" />
	          </s:elseif>
	          <s:elseif test="domain.paystatus < 0">
	          	<s:text name="main.checkout.jsp.paystatus.fail" />
	          </s:elseif>
	          <s:else>
	          	<s:text name="main.checkout.jsp.paystatus.none" />
	          </s:else>
	        </p>
	    </s:if>
        <p>
          <label style="width:160px;float:left;">
            <s:text name="main.checkout.jsp.pay.amount"/>:
          </label>
          &nbsp;
          <s:property value="%{getText('global.format.intmoney',{domain.famount})}"/>
        </p>

        <p/>
    <table class="ui-widget ui-widget-content" style="width:90%;font-size: 12px;">
      <thead>
        <tr>
        	<TH width="3%" nowrap="nowrap"><s:text name="admin.label.comm.serial"/></TH>
        	<TH width="25%" nowrap="nowrap"><s:text name="admin.label.comm.english.title"/></TH>
        	<TH width="8%" nowrap="nowrap"><s:text name="admin.label.goods.color"/></TH>
        	<TH width="8%" nowrap="nowrap"><s:text name="admin.label.orderitem.size"/></TH>
        	<TH width="8%" nowrap="nowrap"><s:text name="admin.label.orderitem.price"/></TH>
        	<TH width="8%" nowrap="nowrap"><s:text name="admin.label.orderitem.quantity"/></TH>
        	<TH width="8%" nowrap="nowrap"><s:text name="admin.label.order.coupon"/></TH>
        	<TH width="8%" nowrap="nowrap"><s:text name="admin.thead.order.amount"/></TH>
        	<TH width="8%" nowrap="nowrap"><s:text name="admin.label.orderitem.subtotal"/></TH>
        	<TH width="8%" nowrap="nowrap"><s:text name="main.checkout.jsp.custom"/></TH>
        	<TH width="16%" nowrap="nowrap"><s:text name="admin.label.orderitem.status"/></TH>
        </tr>
      </thead>
          <s:action var="colorlist" name="dict!retrieveList" namespace="/admin">
          	<s:param name="domain.key">anyshopstyle_clothes_color</s:param>
          </s:action>
      <tbody id="lineitem_body">
      <!-- start page.datas -->
     <s:set value="0" var="totallPrice"/>
      <s:iterator var="item" value="domain.orderLines" status="stat">
      <%-- <tr class="<s:if test="#stat.even">even</s:if><s:else>odd</s:else>"> --%>
        <tr ${stat.even ? '' : 'class="alt-row"'}>
          <td align="center">${stat.count }</td>
          <td>
            <s:property value="%{iscn?title:etitle }"/>
          </td>
          <td align="center">
            <s:property value="%{#colorlist.items.{^ #this.oid == #item.color}[0][iscn?'name':'ename']}"/>
          </td>
          <td align="center">
            <s:property value="sizename"/>
          </td>
          <td align="right">
            <s:property value="%{getText('global.format.intmoney',{price})}"/>
          </td>
          <td align="center">
            <s:property value="quantity"/>
          </td>
          <td align="center">
          	<s:property value="coupno"/>
          </td>
          <td align="center">
          	  <s:if test="catetype != null && catetype == 2">
          	  	${amount}%
          	  </s:if>
          	  <s:else>
          	  	<s:property value="%{getText('global.format.intmoney',{amount})}"/>
          	  </s:else>
          </td>
          <td align="center">
            <s:if test="amount != null">
          		<s:if test="catetype != null && catetype == 2">
          			<s:property value="%{getText('global.format.intmoney',{price*quantity*(amount*0.01)})}"/>
          		</s:if>
          		<s:else>
          			<s:property value="%{getText('global.format.intmoney',{price*quantity - amount})}"/>
          		</s:else>
          	</s:if>
          	<s:else>
          		<s:property value="%{getText('global.format.intmoney',{price*quantity})}"/>
          	</s:else>
          </td>
          <td align="center">
          		<s:property value="custom"/>
          </td>
          <td align="center" style="width:107px;">
            <s:select name="lineitemstatus" value="status" emptyOption="false" list="#{0:'Uncompleted', 1:'Completed' }"
             cssStyle="width:100%;" disabled="true"></s:select>
          </td>
        </tr>
        
      </s:iterator> <!-- End list iterator -->
      </tbody>
     
      <tfoot>
        <tr>
          <td colspan="8" align=right>
            <s:text name="admin.label.order.totallprice"/>: 
            	<s:property value="%{getText('global.format.intmoney',{domain.amount})}"/>
          </td>
        </tr>
      </tfoot>
      
    </table>
    
    <p/>
    <p/>
        
        <div class="managerCell002">
          
          <span class="Button002" id="backbtn">
            <s:text name="admin.article.back"/>
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
	
	$('#backbtn').click(function(event){
		$("#tab2 .Button002").button("disable");
		history.back(-1);
	});
	
	<s:if test="domain.status == 7">
	$('#lineitem_body select').attr('disabled',true);
	</s:if>
});

function doCancel(){
		location = '${ctx}/mi/order!list.action?pageNo=${pageNo}&pageSize=${pageSize}';
}

</script>




</div>
</body>
</html>
