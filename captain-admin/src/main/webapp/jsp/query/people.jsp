<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="ctx" value="#attr['com.opensymphony.xwork2.dispatcher.PageContext'].request.contextPath"/>

<!-- about detail start -->
<div id="" class="about_detail">
    <div class="about_desc">
    	<table>
    		<tr>
    			<s:if test="domain.colorurl != null">
		    		<td valign="top"><img class="about_people_img" src="${ctx}/${domain.colorurl}" style="width:96px;height:92px;" /></td>
		    	</s:if>
		    	<td valign="top">
		    		<p>${domain.content}</p>
		    	</td>
    		</tr>
    	</table>
    
    <%-- <s:iterator value="items">
    	<TABLE>
		<TR>
			<TD vAlign="top">
				<img class="about_people_img" src="${ctx}/${colorurl}" style="width:96px;height:92px;" />
				<img class="about_people_img" src="${ctx}/lookfor/images/test/73pix.png" />
			</TD>
			<TD vAlign="top">
			  <p>${content}</p>
			</TD>
		</TR>
		</TABLE>
    </s:iterator> --%>
	</div>
</div>
<!-- about detail end -->