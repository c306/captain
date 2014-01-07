<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<div class="product">
	<div class="product_title"></div>
	
	<div class="product_list"><div class="product_list_detail">
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
		bar.onProductShow(5);
		$(".product_title").load(ctx + "/query/artle!queryDetail?keys=" + $(this).attr("objid") + "&" + Math.random(), function() {
		});
	});
});
</script>


