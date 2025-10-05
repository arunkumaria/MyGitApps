<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Electricity Charge Calculation Result</title>
</head>
<body>
    <h1>Electricity Charge Calculation Result</h1>
	
	<c:choose>
	       <c:when test="${not empty Name}">
	           <table border="1" cellpadding="5" cellspacing="0">
	               <tr>
	                   <th>Name</th>
	                   <td>${Name}</td>
	               </tr>
	               <tr>
	                   <th>Service Number</th>
	                   <td>${ServiceNumber}</td>
	               </tr>
	               <tr>
	                   <th>Gender</th>
	                   <td>${Gender}</td>
	               </tr>
	               <tr>
	                   <th>Consumed Units</th>
	                   <td>${ConsumedUnits}</td>
	               </tr>
				   <tr>
				        <th>Electricity charges</th>
				        <td>${ElectricityCharges}</td>
				    </tr>
	           </table>
	       </c:when>
	       <c:otherwise>
	           <p style="color:red;">Person not found in database.</p>
	       </c:otherwise>
	   </c:choose>
	

    <p><a href="index.jsp">Back to Input Form</a></p>
</body>
</html>
