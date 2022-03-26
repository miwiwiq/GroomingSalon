<%--
  Created by IntelliJ IDEA.
  User: Kristina
  Date: 24.01.2022
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en_US'}" scope="session"/>
<fmt:setBundle basename="localization" scope="session"/>
<html>
<head>
    <link rel="stylesheet" href="/resources/static/css/styles.css">
</head>
<body>
<jsp:include page="/views/header.jsp"/>
<div class="container" id="container">
    <div class="form-container sign-up-container">
        <form method="post" action="/signUp"
              enctype="application/x-www-form-urlencoded"  accept-charset="utf-8">
            <h1>                    <fmt:message key="createAcc"/>
            </h1>
            <input type="hidden" name="command" value="signUp">
            <input type="text" name="username" placeholder="<fmt:message key="enter.login"/>" class="input" required>
            <input type="password" name="password" placeholder="<fmt:message key="enter.password"/>" class="input" required>
            <input id="name" type="text" name="name" placeholder="<fmt:message key="enter.name"/>" class="input" required>
            <input id="surname" type="text" name="surname" placeholder="<fmt:message key="enter.surname"/>" class="input" required>
            <input id="phone" type="tel" name="phone" placeholder="<fmt:message key="enter.phone"/>" pattern="[0-9]{3}[0-9]{2}[0-9]{7}" class="input"
                   required>
            <input id="email" type="email" name="email" placeholder="<fmt:message key="enter.email"/>" class="input"
                   required>
            <input type="submit" class="ghost" name="<fmt:message key="signUp"/>">
        </form>
    </div>
    <div class="form-container sign-in-container">
        <form method="post" action="signIn"
              enctype="application/x-www-form-urlencoded">
            <h1><fmt:message key="loginAcc"/></h1>
            <input type="hidden" name="command" value="signIn">
            <input id="login" type="text" name="username" placeholder="<fmt:message key="enter.login"/>" class="input" required>
            <input id="pass" type="password" name="password" placeholder="<fmt:message key="enter.password"/>" class="input"
                   required>
            <input type="submit" class="ghost" name="<fmt:message key="signIn"/>">
        </form>
    </div>
    <div class="overlay-container">
        <div class="overlay">
            <div class="overlay-panel overlay-left">
                <h1><fmt:message key="welcomeBack"/></h1>
                <p><fmt:message key="txt1"/></p>
                <button class="ghost" id="signIn"><fmt:message key="signIn"/></button>
            </div>
            <div class="overlay-panel overlay-right">
                <h1><fmt:message key="hello"/></h1>
                <p><fmt:message key="txt2"/></p>
                <button class="ghost" id="signUp"><fmt:message key="signUp"/></button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/index.js"></script>
</body>
</html>
