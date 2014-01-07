<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<link rel="stylesheet" type="text/css" href="${ctx}/lookfor/css/banner.css"></link>
<script type="text/javascript" src="${ctx}/lookfor/js/SlideTrans.min.js"></script>

<div class="product">
	<div class="product_title">
		
		<!-- start -->
		<div class="product_core">
			<div id="idContainer" class="product_image_zone middlePicSlider">
				<div onclick="bar.onProductClose(5);" class="product_detailclose" style="display:none;"></div>
				<div onclick="st.Previous();" class="directionPrev"></div>
				<table id="idSlider" border="0" cellspacing="0" cellpadding="0" style="display:none;">
					<tr><!-- moduleid =  2黑白图片, 3彩色图片, 4明细大图片 -->
						<s:set name="attsize" value="0" />
						<s:iterator value="domain.atts">
							<s:if test="moduleid == 4">
								<s:set name="attsize" value="#attsize + 1" />
								<td class="td_f">
									<img src="${ctx}/${uri}" class="product_image" style="cursor:pointer;border:0px;width:359px;"/>
								</td>
							</s:if>
						</s:iterator>
						<%-- <td class="td_f">
							<img src="${ctx}/lookfor/images/test/product/product/310pixX333pix.png" class="product_image" style="cursor:pointer;border:0px;width:359px;"/>
						</td>
						<td class="td_f">
							<img src="${ctx}/lookfor/images/test/product/product/310pixX333pix.png" class="product_image" style="cursor:pointer;border:0px;width:359px;"/>
						</td> --%>
					</tr>
				</table>
				<div onclick="st.Next();" class="directionNext"></div>
			</div>
			<div class="product_draft">
				${domain.content}
				<%-- <img src="${ctx}/lookfor/images/test/product/product/215pixX333pix.png" style="width:167px;"/> --%>
			</div>
		</div>
		<div class="product_submit_01">
			<div class="product_sbtn">
				<div class="pruduct_sbutton" onclick="prdt_submt();"><s:text name="main.main.jsp.submit" /></div>
			</div>
			<input type="hidden" id="msgoid" value="${domain.oid}" />
			<input type="hidden" id="msgtitle" value="${domain.title}" />
			<div class="product_smsg_01" contenteditable="true" onfocus="_clear(this);"
					onblur="_clear(this);"><s:text name="main.main.jsp.inquiry.content" /></div>
		</div>
		<!-- end -->
		
	</div>
	
	<div class="product_list_01"><div class="product_list_detail">
		<s:iterator var="art" value="items" status="aidx">
			<div id="pitem_${aidx.index}" class="pitem" 
				objid="${art.oid}"
				gbg="url(${ctx}/${art.blackurl})"
				style="background-image:url(${ctx}/${art.blackurl});">
				<ul style="display: none;"><!-- moduleid =  2黑白图片, 3彩色图片, 4明细大图片 -->
					<s:iterator value="#art.atts">
						<s:if test="moduleid == 3">
							<li><img src="${ctx}/${uri}" width="110" height="118" /></li>
						</s:if>
					</s:iterator>
					<%-- <li><img src="${ctx}/lookfor/images/test/product/product/110pixX118pix_o.png" width="110" height="118" /></li>
					<li><img src="${ctx}/lookfor/images/test/product/product/110pixX118pix_o.png" width="110" height="118" /></li>
					<li><img src="${ctx}/lookfor/images/test/product/product/110pixX118pix_o.png" width="110" height="118" /></li> --%>
				</ul>
				<div id="ptitlediv" class="pitem_title pitem_transparent">${art.title}</div>
			</div>
		</s:iterator>
	</div></div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	btns = $(".pitem");
	btns.hover(function() {
		$(this).children("#ptitlediv").removeClass("pitem_title").addClass("pitem_title_active");
		
		itemnme = "#pitem_" + $(this).index();
		var ul = $(itemnme + " ul");
		var li = $(itemnme + " li");
		$(this).css({"background-image":""});
		ul.css({"display":""});
		var n = li.length;
		timer = setInterval(function(){
			n = n>=li.length?0:n;
			ul.css({"left":"0px"});
			li.css({"left":"0px"});
			li.eq(n).css({"left":"110px"});
		 	
			n++;
			ul.animate({left:"-110px"}, 350);
		}, 1000);
	}, function() {
		clearInterval(timer);
		
		$(this).children("#ptitlediv").removeClass("pitem_title_active").addClass("pitem_title");
		$(this).css({"background-image":$(this).attr("gbg")});
		$("#pitem_" + $(this).index() + " ul").css({"display":"none"})
	});
	btns.click(function() {
		$(".product_title").load(ctx + "/query/artle!queryDetail?keys=" + $(this).attr("objid") + "&" + Math.random(), function() {
			bar.onProductShow(5);
		});
	});
});

var st;
$(document).ready(function() {
	var forEach = function(array, callback, thisObject) {
		if(array.forEach) array.forEach(callback, thisObject);
		else for (var i = 0, len = array.length; i < len; i++) callback.call(thisObject, array[i], i, array);
	}
	var size = '${attsize}';
	st = new SlideTrans("idContainer", "idSlider", size, { Vertical: false, Auto: false, Change: 359 });
	st.Run();
	$("#idSlider").show();
});
draft = '<s:text name="main.main.jsp.inquiry.content" />';
function prdt_submt() {
	if($('.product_smsg_01').html().trim() == null 
			|| $('.product_smsg_01').html().trim() == "" 
			|| $('.product_smsg_01').html().trim() == draft) {
		alert('<s:text name="main.main.jsp.enter.content" />');
	} else {
		$.ajax({
			url: ctx + "/query/email!insertInfo",
			data: {'domain.objid':$("#msgoid").val(),'domain.title':$("#msgtitle").val(),'domain.note':$('.product_smsg_01').html()},
			cache: false,
			async: true,
			dataType: "json",
			success: function(data) {
				if(data == '1') {
					alert('<s:text name="main.main.jsp.send.success" />');
					$('.product_smsg_01').html('');
				} else {
					alert('<s:text name="main.main.jsp.send.fail" />');
				}
			},
			error: function(){
				alert('Error !');
			}
		});
	}
}
</script>