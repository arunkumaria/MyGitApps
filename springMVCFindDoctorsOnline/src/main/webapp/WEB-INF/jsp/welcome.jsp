<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Doctor Details</title>
</head>
<body>
    <h1>Doctor Details</h1>
    
    <c:choose>
        <c:when test="${not empty DoctorName}">
            <table border="1" cellpadding="5" cellspacing="0">
                <tr>
                    <th>Name</th>
                    <td>${DoctorName}</td>
                </tr>
                <tr>
                    <th>Registration Number</th>
                    <td>${RegistrationNumber}</td>
                </tr>
                <tr>
                    <th>Gender</th>
                    <td>${Gender}</td>
                </tr>
                <tr>
                    <th>Qualification</th>
                    <td>${Qualification}</td>
                </tr>
            </table>
        </c:when>
        <c:otherwise>
            <p style="color:red;">No doctor found for the given details.</p>
        </c:otherwise>
    </c:choose>

    <br><br>
    <a href="doctorsearchform">Search Again</a>
</body>
</html>
