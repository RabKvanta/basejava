<%@ page import="ru.javawebinar.basejava.model.ExperienceSection" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"  charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType,java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
        <table>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType,ru.javawebinar.basejava.model.AbstractSection>"/>
            <!-- devcolibri.com/jstl-для-написания-jsp-страниц/ -->
                <c:set var="section" value="${sectionEntry.value}"/>
                <c:set var="type"    value="${sectionEntry.key}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.AbstractSection"/>
            <jsp:useBean id="type" type="ru.javawebinar.basejava.model.SectionType"/>
            <c:choose>
               <c:when test="${type==SectionType.PERSONAL || type==SectionType.OBJECTIVE}">
                  <tr>
                     <td>
                         <h3>${type.title}</h3>
                      <%=((TextSection) section).getContent()%>
                    </td>
                </tr>

                </c:when>
                <c:when test="${type==SectionType.ACHIEVEMENT || type==SectionType.QUALIFICATION}">
                    <tr> <td>
                                <h3>${type.title}</h3>
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                        </td>
                    </tr>

                </c:when>
                <c:when test="${type==SectionType.EDUCATION || type==SectionType.EXPERIENCE}">
                    <c:forEach var="exp" items="<%=((ExperienceSection) section).getExperiences()%>">
                        <tr>
                            <td>
                             <a href="${exp.homePage.url}">${exp.homePage.name}</a>
                            </td>
                        </tr>
                        <c:forEach var="position" items="${exp.positions}">
                            <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Experience.Position"/>
                            <tr>
                                <td><b>
                                    <br> <%=DateUtil.toString(position.getStartDate()) + "-" + DateUtil.toString(position.getEndDate())%></br>
                                    <%=type.equals(SectionType.EXPERIENCE)? "Должность: ":" - "%>${position.title}&nbsp&nbsp${position.description}
                                    </b>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    Если не одно условие не есть верно.
                </c:otherwise>
            </c:choose>
        </c:forEach>
        </table>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
