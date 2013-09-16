<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ciao</title>
         <link rel="stylesheet" type="text/css" href="<spring:url value="/css/style.css" />" />


    </head>
    <body>
        <div id="mainblock">
        <h2 style="margin:auto">TorCommunity Prototype</h2>
        <p>TorCommunity is still in developement, please, don't use it in productio.</p>
        <a href="<spring:url value="/web/makeaccount"/>">Make a new Account</a><br/>
        </div>

    </body>
</html>
