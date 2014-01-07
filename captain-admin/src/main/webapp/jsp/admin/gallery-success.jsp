<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="/common/taglibs.inc"%>
<%long currentTimeMillis = System.currentTimeMillis();%>
<html>
<head>
    <title>Showcase</title>
</head>
<body>
<script type="text/javascript">
var html = '';

    	html += '<li class="ui-widget-content ui-corner-tr" oid="<%=currentTimeMillis%>">'+
    	'<h5 class="ui-widget-header">Gallery</h5>'+
    	'<img class="preview" title="<s:property value="caption" />"'+
		' filename="<s:property value="uploadFileName" />"' +
		' objid=""' +
		' oid=""' +
		' moduleid=""' +

' src="${ctx}<s:property value="previewImg" />"' +

    	' uri="<s:property value="destFileName" />"'+
    	' />'+
    	'<a href="${ctx}/<s:property value="destFileName" />" target="_blank" title="View larger image" class="ui-icon ui-icon-zoomin"></a>'+
    	'<a href="javascript:$(document).data(\'${param.GUID}\').removeUploadFile(\'<%=currentTimeMillis%>\');" title="Delete this image" class="ui-icon ui-icon-trash"></a>'+
    	'</li>';

try{
	if(parent.uploadCompeled)
		parent.uploadCompeled(html ,'${param.GUID}');
}catch(e){alert("200:"+e.message);}

</script>
</body>
</html>
