<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>System Informations</title>
    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  <div style="color:#FF0000;">
    Default TimeZone: <%=java.util.TimeZone.getDefault().getID() %>
  </div>
  <div style="color:#FF0000;">
    basePath: <%=basePath%>
  </div>
  <div style="color:#FF0000;">
    new java.io.File(".").getAbsolutePath(): <%=new java.io.File(".").getAbsolutePath()%>
  </div>
  
<%
java.util.Properties pro = System.getProperties();
java.util.Iterator it=pro.entrySet().iterator();
while(it.hasNext()){
	java.util.Map.Entry entry=(java.util.Map.Entry)it.next();
    Object key = entry.getKey();
    Object value = entry.getValue();
    out.print(key +"="+value);
    out.print("<br/>");
}

Runtime lRuntime = Runtime.getRuntime();

out.println("*** BEGIN MEMORY STATISTICS ***<br/>");

out.println("Free Memory: "+lRuntime.freeMemory()+"<br/>");

out.println("Max Memory: "+lRuntime.maxMemory()+"<br/>");

out.println("Total Memory: "+lRuntime.totalMemory()+"<br/>");

out.println("Available Processors : "+lRuntime.availableProcessors()+"<br/>");

out.println("*** END MEMORY STATISTICS ***");
%>
  </body>
</html>
