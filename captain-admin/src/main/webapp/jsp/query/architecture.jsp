<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<script type="text/javascript" src="${ctx}/lookfor/js/SlideTrans.min.js"></script>

<!-- reseach detail start -->
<div id="reseach_detail" class="reseach_detail">
	<div id="idContainer" class="reseach_carousel">
		<div onclick="st.Previous();" class="directionPrev"></div>
		<table id="idSlider" border="0" cellspacing="0" cellpadding="0" style="display:none;">
			<tr>
				<s:set name="attsize" value="0" />
				<s:iterator value="domain.atts">
					<s:if test="moduleid == 4">
						<s:set name="attsize" value="#attsize + 1" />
						<td class="td_f">
							<img src="${ctx}/${uri}" class="reseach_image" style="cursor:pointer;border:0px;width:528px;"/>
						</td>
					</s:if>
				</s:iterator>
			</tr>
		</table>
		<div onclick="st.Next();" class="directionNext"></div>
	</div>
	
	<div class="reseach_desc">${domain.content}</div>
</div>
<!-- reseach detail end -->

<script type="text/javascript">
var st;
$(document).ready(function() {
	var forEach = function(array, callback, thisObject) {
		if(array.forEach) array.forEach(callback, thisObject);
		else for (var i = 0, len = array.length; i < len; i++) callback.call(thisObject, array[i], i, array);
	}
	var size = '${attsize}';
	st = new SlideTrans("idContainer", "idSlider", size, { Vertical: false, Auto: false, Change: 528 });
	st.Run();
	$("#idSlider").show();
});
function onResearchClose() {
	bar.onNavigKey(2, 15);
}
</script>

