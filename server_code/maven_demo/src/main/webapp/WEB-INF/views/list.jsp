<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
书籍列表:
<br>

<table>
    <tr>
        <th>编号</th>
        <th>书名</th>
    </tr>
    <tbody>
    <c:forEach items="${book}" var="books" >
        <tr>
            <td>${books.bookid}</td>
            <td>${books.bookname}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>