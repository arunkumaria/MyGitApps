<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Find Doctors Online</title>
</head>
<body>

    <h1>Find Doctors Online</h1>

    <form:form method="post" 
               action="${pageContext.request.contextPath}/checkDoctorsOnline"
               modelAttribute="doctor">

        <table>
            <tr>
                <td>Doctor Name:</td>
                <td><form:input path="doctorName"/></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center;">(Or)</td>
            </tr>
            <tr>
                <td>Registration Number:</td>
                <td><form:input path="doctorRegistrationNumber"/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Check Doctors Online"/></td>
            </tr>
        </table>
    </form:form>

    <br><br>
    <center>
        <b><a href="${pageContext.request.contextPath}/doctorsearchform">Find Doctors Online</a></b>
    </center>

</body>
</html>
