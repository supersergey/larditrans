<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sergey
  Date: 14.04.2016
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
<head>
    <title>View phone book records</title>

</head>
<body>
<table class="table table-hover">
  <thead>
  <tr>

  <th>Фамилия</th>
  <th>Имя</th>
  <th>Отчество</th>
  <th>Мобильный тел.</th>
  <th>Домашний тел.</th>
  <th>Адрес</th>
  <th>Е-почта</th>
  </tr>
  </thead>

  <c:forEach items="${entries}" var="entry">
    <tr>
    <td>${entry.lastName}</td>
    <td>${entry.firstName}</td>
    <td>${entry.patronymic}</td>
    <td>${entry.cellNumber}</td>
    <td>${entry.phoneNumber}</td>
    <td>${entry.address}</td>
    <td>${entry.email}</td>
    </tr>
  </c:forEach>

</table>
</body>
</html>
