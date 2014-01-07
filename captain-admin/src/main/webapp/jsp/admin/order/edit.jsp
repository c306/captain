<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<s:set value="%{getText('admin.navi.headline.title.ordermanagement')}" var="pagetitle"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ include file="/jsp/admin/include/head.inc"%>

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

<s:form id="appfrm" action="/admin/order!orderProcess.action" method="post" enctype="multipart/form-data">
<!-- 查询条件 -->
    <s:hidden name="pageNo"/>
    <s:hidden name="pageSize"/>
    <s:hidden name="sort"/>
    <s:hidden name="dir"/>
    <s:hidden name="query.key"/>
    <s:hidden name="query.ename"/>
    <s:hidden name="query.name"/>
    <s:hidden name="query.status"/>
<!-- 	隐藏域 -->
	<s:token />
	<s:hidden name="domain.orderno"/>
	<s:hidden name="domain.status"/>
	<s:hidden name="domain.createtime"/>
	<s:hidden name="domain.compressname"/>
	<s:hidden name="domain.compresssn"/>
    
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
        <s:if test="domain.compresssn != null && domain.compresssn != ''">
        <p>
          <label style="width:160px;float:left;">
            <s:text name="admin.label.order.compressage.serialnumber"/>:
          </label>
          &nbsp;
          ${domain.compresssn }
        </p>
        </s:if>
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
    <table class="cnttbl" style="font-size: 12px;">
      
      <thead>
        <tr>
        	<TH width="1%" nowrap="nowrap">
              <%-- <s:text name="admin.label.comm.serial"/> --%>
              <s:text name="admin.label.orderitem.picture"/>
            </TH>
        	<TH width="25%" nowrap="nowrap" align="left">
              <s:text name="admin.label.comm.english.title"/> /
              <s:text name="admin.label.goods.color"/> /
        	  <s:text name="admin.label.orderitem.size"/>
            </TH>
        	<TH width="8%" nowrap="nowrap">
              <s:text name="admin.label.orderitem.price"/> X
        	  <s:text name="admin.label.orderitem.quantity"/>
            </TH>
        	<TH width="8%" nowrap="nowrap"><s:text name="admin.thead.order.amount"/>(<s:text name="admin.label.order.coupon"/>)</TH>
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
        <s:action namespace="/admin" name="upload!list" executeResult="false" var="pics">
          <s:param name="domain.objid" value="goodsno" />
        </s:action>
      <%-- <tr class="<s:if test="#stat.even">even</s:if><s:else>odd</s:else>"> --%>
        <tr ${stat.even ? '' : 'class="alt-row"'}>
          <%-- <td align="center">${stat.count }</td> --%>
          <td align="center">
            <img src="${ctx}/zoom/${attr.pics.items[0].uri}" border="0" />
          </td>
          <td>
            <a href="javascript:void(0);"></a><s:property value="%{iscn?title:etitle }"/>
            <br />
            <s:text name="admin.label.goods.color"/>:
            <s:property value="%{#colorlist.items.{^ #this.oid == #item.color}[0][iscn?'name':'ename']}"/>
            <br />
            <s:text name="admin.label.orderitem.size"/>:
            <s:property value="sizename"/>
          </td>
          <td align="center">
            <nobr>
            <s:property value="%{getText('global.format.intmoney',{price})}"/>
            X
            <s:property value="quantity"/>
            </nobr>
          </td>
          <td align="center">
            <s:if test="amount != null">
          	  <s:if test="catetype != null && catetype == 2">
          	  	${amount}%(<s:property value="coupno"/>)
          	  </s:if>
          	  <s:else>
          	  	<s:property value="%{getText('global.format.intmoney',{amount})}"/>(<s:property value="coupno"/>)
          	  </s:else>
            </s:if>
          </td>
          <td align="right">
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
             cssStyle="width:100%;" onchange="ajaxOrderLineItemProcess(this, '%{#item.oid}')"></s:select>
          </td>
        </tr>
        
      </s:iterator> <!-- End list iterator -->
      </tbody>
     
      <tfoot>
        <tr>
          <td colspan="10" align=right>
            <s:text name="admin.label.order.totallprice"/>:
            	<s:property value="%{getText('global.format.intmoney',{domain.amount})}"/>
          </td>
        </tr>
      </tfoot>
      
    </table>
    
    

        <div class="managerCell002">
    
        <s:if test="domain.status == 0">
          <span class="Button002" id="processbtn1">
            <s:text name="admin.order.button.label.save1"/>
          </span>
          <%-- 
          <span class="Button002" id="processbtn2">
            <s:text name="admin.order.button.label.save2"/>
          </span>
          <span class="Button002" id="processbtn3">
            <s:text name="admin.order.button.label.save3"/>
          </span>
          <span class="Button002" id="processbtn4">
            <s:text name="admin.order.button.label.save4"/>
          </span>
           --%>
          <span class="Button002" id="processbtn5">
            <s:text name="admin.order.button.label.save5"/>
          </span>
        </s:if>
        <s:if test="domain.status == 6">
          <span class="Button002" id="processbtn6">
            <s:text name="admin.order.button.label.save6"/>
          </span>
         </s:if>
          
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

<div id="Compressage-dialog-form" title="Compressage-dialog-form">
  <form>
  <fieldset>
<%--    <label for="couponAmount"><s:text name="admin.label.order.compressage.corporation"/></label>--%>
<%--    <input type="text" name="compressagecorporation" id="compressagecorporation" class="text ui-widget-content ui-corner-all"--%>
<%--     value="顺风快递" />--%>
    <label for="Quantity"><s:text name="admin.label.order.compressage.serialnumber"/></label>
    <input type="text" name="compressageserialnumber" id="compressageserialnumber" class="text ui-widget-content ui-corner-all" />
  </fieldset>
  </form>
</div>
<script type="text/javascript">

$(document).ready(function(){

	$("#tab2 .Button002").button().css({'margin-left':2, height:35}).find('span').css({'text-decoration': 'underline'});
	
	$('#processbtn1').click(function(event){
		$("#tab2 .Button002").button("disable");
		$("#appfrm_domain_status").val(6);
		$('#appfrm')[0].submit();
	});
	/*
	$('#processbtn2').click(function(event){
		$("#tab2 .Button002").button("disable");
		$("#appfrm_domain_status").val(2);
		$('#appfrm')[0].submit();
	});
	$('#processbtn3').click(function(event){
		$("#tab2 .Button002").button("disable");
		$("#appfrm_domain_status").val(3);
		$('#appfrm')[0].submit();
	});
	$('#processbtn4').click(function(event){
		$("#tab2 .Button002").button("disable");
		$("#appfrm_domain_status").val(4);
		$('#appfrm')[0].submit();
	});
	*/
	$('#processbtn5').click(function(event){
		$("#tab2 .Button002").button("disable");
		$("#appfrm_domain_status").val(5);
		$('#appfrm')[0].submit();
	});
	$('#processbtn6').click(function(event){
		//$(':input[name="lineitemstatus"][]').each(function(){alert(999);});
		if($('#lineitem_body select option:selected[value="0"]').length>0){
			alert('<s:text name="admin.order.msg.isnotallcomplete"/>');
			return false;
		}
		$( "#Compressage-dialog-form" ).dialog('open');
	});
	$('#backbtn').click(function(event){
		$("#tab2 .Button002").button("disable");
		$('#appfrm')[0].action = '${ctx}/admin/order!list.action';
		$('#appfrm').submit();
	});
	
	<s:if test="domain.status == 7">
	$('#lineitem_body select').attr('disabled',true);
	</s:if>
	
	

	$( "#Compressage-dialog-form" ).dialog({
	  	autoOpen: false,
	  	height: 150,
	  	width: '400',
	  	modal: true,
	  	buttons: {
	  		"<s:text name='global.button.label.submit'/>": function() {
	  			$("#tab2 .Button002").button("disable");
	  			//快递号
	  			$("#appfrm_domain_compresssn").val($("#compressageserialnumber").val());
	  			$("#appfrm_domain_status").val(7);
	  			$('#appfrm')[0].submit();
	  			//
	  			$( this ).dialog( "close" );
	  		},
	  		"<s:text name='global.button.label.cancel'/>": function() {
	  			$( this ).dialog( "close" );
	  		}
	  	},
	  	close: function() {
	  	}
	  });
});
function resizeImage(iwidth, iheight){
	var image = new Image();
	image.src = this.src;
	
	if(image.width>0 && image.height>0){
		if(image.width/image.height >= iwidth/iheight){
			if(image.width > iwidth){
				$(this).css({width: iwidth, height: (image.height*iwidth)/image.width});
			}
		}else{
			if(image.height > iheight){
				$(this).css({width: (image.width*iheight)/image.height, height: iheight});
			}
		}
	}
}
/**
 * 处理订单条目：未完成、完成
 */
function ajaxOrderLineItemProcess(el, itemoid){
	$("#tab2 .Button002").button("disable");
	$.ajax({
		  url: "${ctx}/admin/order!ajaxOrderLineItemProcess.action",
		  data: {'oid': itemoid, 'domain.status': $(el).val()},
		  cache: false,
		  async: true,
		  dataType: "json",
		  success: function(res){
			  if(res.success){
				  $("#tab2 .Button002").button("enable");
			  } else {
				  alert('Process Faild !');
			  }
		  },
		  error: function(){
			  alert('Error !');
		  }
		});
}

function doCancel(){
		location = '${ctx}/admin/order!list.action?pageNo=${pageNo}&pageSize=${pageSize}';
}

</script>


<%@ include file="/jsp/admin/include/foot.inc"%>