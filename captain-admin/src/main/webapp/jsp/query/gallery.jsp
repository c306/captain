<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<s:iterator var="item" value="items" status="stat">
  <div class="item transparencyzero col${item.w } row${item.h }" w="${item.w}" h="${item.h}" x="${item.x}" y="${item.y}" type="${item.type}" aid="${item.articleId}" style="position: absolute; background-image: url(${pageContext.request.contextPath}${item.url });" >
  </div>
</s:iterator>


