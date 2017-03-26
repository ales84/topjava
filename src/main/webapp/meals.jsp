<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Моя еда</h2>
<a href="meals?action=insert">Добавить еду</a><br/>
<table border="1" align="left" frame="void" width="80%">
    <thead>
    <tr>
        <th>Дата и время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <tr style="${meal.exceed ? "color: #ff0000" : "color: #009900"}">
            <td><c:out value="${meal.dateTime}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Обновить</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Удалить</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
