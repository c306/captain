<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<!-- about detail start -->
<div id="" class="about_people_detail">
	<div class="about_people_desc">
		<div id="showMap" style="border:0px;width:528px;height:363px;"></div>
		<script type="text/javascript">
		var add_text = "${domain.address}";
		</script>
		<s:if test="domain.blackurl == 'google'">
			<script type="text/javascript">
			var map = new GMap2(document.getElementById("showMap"));
			var geocoder = new GClientGeocoder();
			function showAddress(address) {
				geocoder.getLatLng(address, function(point){
					map.setCenter(point, 18);
					var marker = new GMarker(point);
					map.addControl(new GLargeMapControl());
					map.addOverlay(marker);
					marker.openInfoWindowHtml(address);
				});
			}
			showAddress(add_text);
			</script>
		</s:if>
		<s:else>
			<script type="text/javascript">
			var map = new BMap.Map("showMap");
			map.centerAndZoom(new BMap.Point(116.404, 39.915),10);
			var myGeo = new BMap.Geocoder();
			myGeo.getPoint(add_text, function(point){
				if(point) {
					map.centerAndZoom(point, 18);
					map.addOverlay(new BMap.Marker(point));
				}
			}, '');
			map.addControl(new BMap.NavigationControl({anchor:BMAP_ANCHOR_TOP_LEFT,type:BMAP_NAVIGATION_CONTROL_LARGE}));
			//向地图中添加缩略图控件
			var ctrl_ove = new BMap.OverviewMapControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT,isOpen:1});
			map.addControl(ctrl_ove);
			//向地图中添加比例尺控件
			var ctrl_sca = new BMap.ScaleControl({anchor:BMAP_ANCHOR_BOTTOM_LEFT});
			map.addControl(ctrl_sca);
			</script>
		</s:else>
		
		<%-- <img src="${ctx}/${domain.colorurl}" style="border:0px;width:528px;height:363px;" /> --%>
	</div>
	<div class="product_submit">
		<div class="product_sbtn">
			<div class="pruduct_sbutton" onclick="prdt_submt();"><s:text name="main.main.jsp.submit" /></div>
		</div>
		<input type="hidden" id="msgoid" value="${domain.oid}" />
		<input type="hidden" id="msgtitle" value="${domain.title}" />
		<input type="hidden" id="msgcontent" value="${domain.content}" />
		<div class="product_smsg" contenteditable="true" onfocus="_clear(this);"
			onblur="_clear(this);"><s:text name="main.main.jsp.emain.content" /></div>
	</div>
</div>
<!-- about detail end -->

<script type="text/javascript">
$("#finder0").html($("#msgcontent").val());
draft = '<s:text name="main.main.jsp.emain.content" />';
function prdt_submt() {
	if($('.product_smsg').html().trim() == null 
			|| $('.product_smsg').html().trim() == "" 
			|| $('.product_smsg').html().trim() == draft) {
		alert('<s:text name="main.main.jsp.enter.content" />');
	} else {
		$.ajax({
			url: ctx + "/query/email!insertInfo",
			data: {'domain.objid':$("#msgoid").val(),'domain.title':$("#msgtitle").val(),'domain.note':$('.product_smsg').html(),'type':'1'},
			cache: false,
			async: true,
			dataType: "json",
			success: function(data) {
				if(data == '1') {
					alert('<s:text name="main.main.jsp.send.success" />');
					$('.product_smsg').html('');
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