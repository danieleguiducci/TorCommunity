<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ciao</title>
        <meta name="viewport" 
              content="initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>


    </head>
    <body>
        <a href="<spring:url value="/makeaccount"/>">Make a new Account</a><br/>
        dom: <c:out value="${dominio}"/>

    </body>
</html>
