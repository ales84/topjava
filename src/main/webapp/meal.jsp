<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal edit</title>
</head>
<body>
<form method="post" action="meals" name="frmAddMeal">
    <input type="hidden" readonly="readonly" name="id" value="<c:out value="${meal.id}"/>"/>
    Дата и время: <input type="datetime" name="dateTime" value="<c:out value="${meal.dateTime}"/>"/><br/>
    Описание: <input type="text" name="description" value="<c:out value="${meal.description}"/>"/><br/>
    Калории: <input type="number" name="calories" value="<c:out value="${meal.calories}"/>"/><br/>
    <<input type="submit" value="Отправить"/>
</form>
</body>
</html>
