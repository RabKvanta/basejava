<%@ page import="ru.javawebinar.basejava.model.ExperienceSection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>

        <c:forEach var="type" items="<%=SectionType.values()%>">
            <jsp:useBean id="type" type="ru.javawebinar.basejava.model.SectionType"/>
            <dl>
            <dt>${type.title}</dt>
            <c:if test="${resume.getSection(type)!=null}">
                <c:set var="section" value="${resume.getSection(type)}"/>
                <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>

                <c:choose>
                    <c:when test="${type==SectionType.PERSONAL || type==SectionType.OBJECTIVE}">
                    <dd><input type="text" name="${type.name()}" size=30  value="<%=((TextSection)section).getContent()%>"></dd>
                    </c:when>

                    <c:when test="${type==SectionType.ACHIEVEMENT || type==SectionType.QUALIFICATION}">
                        <br><c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                        <dd><input type="text" name="${type.name()}" size=30 value="${item}"></dd><br/>
                        </c:forEach><br/>
                    </c:when>

                    <c:when test="${type==SectionType.EDUCATION || type==SectionType.EXPERIENCE}">
                        <br/>
                            <ol>
                            <c:forEach var="exp" items="<%=((ExperienceSection) section).getExperiences()%>">
                            <jsp:useBean id="exp" type="ru.javawebinar.basejava.model.Experience"/>
                                <c:set var="cnt" value="0"/>
                                <li>
                                    <dt><%=(type==SectionType.EDUCATION)?"Место учебы:":"Место работы:"%></dt>
                                <dd><input type="text" name="${type.name()}name" size=45 value="${exp.homePage.name}"></dd>
                                    <dt>WWW:</dt>
                                <dd><input type="text" name="${type.name()}url" size=30 value="${exp.homePage.url}"></dd>
                                <br><br/>
                                <c:forEach var="position" items="${exp.positions}">
                                   <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Experience.Position"/>
                                    <dt>Период c </dt>
                                    <dd><input type="text" name="${type.name()}${cnt}start" size=30 value="<%=DateUtil.toString(position.getStartDate())%>"></dd>
                                    <dt>по </dt>
                                    <dd><input type="text" name="${type.name()}${cnt}end" size=30 value="<%=DateUtil.toString(position.getEndDate())%>"></dd>
                                    <br><dt> Должность: </dt>
                                    <dd><input type="text" name="${type.name()}${cnt}title" size=30 value="${position.title}"></dd>
                                    <dt>Описание: </dt>
                                    <dd><input type="text" name="${type.name()}${cnt}dscr" size=30 value="${position.description}"></dd><br/>

                                    </c:forEach>
                                    <br><br/>
                                </li>
                            </c:forEach>
                        </ol>
                    </c:when>
                    <c:otherwise>
                        Ни одно условие не верно.
                    </c:otherwise>
                     </c:choose>
                    </c:if>
                <c:if test="${resume.getSection(type)==null}">

                    <c:choose>
                        <c:when test="${type==SectionType.PERSONAL || type==SectionType.OBJECTIVE}">
                            <dd><input type="text" name="${type.name()}" size=30  value=""></dd>
                        </c:when>

                        <c:when test="${type==SectionType.ACHIEVEMENT || type==SectionType.QUALIFICATION}">
                            <dd><input type="text" name="${type.name()}" size=30 value="${item}"></dd><br/>
                        </c:when>

                        <c:when test="${type==SectionType.EDUCATION || type==SectionType.EXPERIENCE}">
                            <br/>
                            <ol>
                                    <li>
                                        <dt><%=(type==SectionType.EDUCATION)?"Место учебы:":"Место работы:"%></dt>
                                        <dd><input type="text" name="${type.name()}" size=45 value=""></dd>
                                        <dt>WWW: </dt>
                                        <dd><input type="text" name="${type.name()}" size=30 value=""></dd>
                                        <br><br/>
                                            <dt>Период c </dt>
                                            <dd><input type="text" name="${type.name()}" size=30 value=""></dd>
                                            <dt>    по </dt>
                                            <dd><input type="text" name="${type.name()}" size=30 value=""></dd>
                                            <br><dt> Должность: </dt>
                                            <dd><input type="text" name="${type.name()}" size=30 value=""></dd>
                                            <dt>   Описание: </dt>
                                            <dd><input type="text" name="${type.name()}" size=30 value=""></dd><br/>
                                        <br><br/>
                                    </li>
                            </ol>
                        </c:when>
                        <c:otherwise>
                            Ни одно условие не верно.
                        </c:otherwise>
                    </c:choose>
                </c:if>

            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
