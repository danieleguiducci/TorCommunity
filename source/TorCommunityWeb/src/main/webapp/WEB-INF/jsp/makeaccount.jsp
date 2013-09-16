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
        <form method="POST" action="<spring:url value="/web/makeaccountdo" />">
            Username:<input type="text" name="username" placeholder="username"/><br/>
            Password will be composed using all the next fields. The sum of the chars of all fields cannot be less than 20<br/>
            your favorate word:  <input type="text" name="password1" placeholder=""/><br/>
            your favorate color: <input type="text" name="password2"/><br/>
            your lucky number: <input type="text" name="password3"/><br/>
            a simple text: <input type="text" name="password4"/><br/>
            <input type="submit" value="Make new account"/>
        </form>

    </body>
</html>
