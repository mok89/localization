<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%

String bssids= request.getParameter("bssid");
String cmd= request.getParameter("cmd");
if(bssids!=null){
	%>
	bssid not null
	<%
}else if(cmd!=null){
	%>
	cmd not null
	<%
}


%>
