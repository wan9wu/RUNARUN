<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
票务信息列表:
<br>

<table>
    <tr>
        <th>编号</th>
        <th>名称</th>
        <th>介绍</th>
        <th>条件</th>
        <th>价格</th>
        
        <th>数量</th>
    </tr>
    <tbody>
    <c:forEach items="${ticket}" var="ticket" >
        <tr>
            <td width=100px; align="center">${ticket.id}</td>
            <td width=100px; align="center">${ticket.name}</td>
            <td width=200px; align="center">${ticket.production}</td>
            <td width=100px; align="center">${ticket.condition}</td>
            <td width=100px; align="center">${ticket.price}</td>
            <td width=100px; align="center">${ticket.count}</td>
            
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>