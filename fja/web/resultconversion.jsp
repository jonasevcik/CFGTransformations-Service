<%-- 
    Document   : result
    Created on : 11.4.2009, 14:37:31
    Author     : NICKT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="Content-Language" content="cs" />
        <link rel="STYLESHEET" type="text/css" href="./style/style.css" />
        <link href="./style/favicon.gif" rel="icon" type="image/gif" />
        <script src="./scripts/util.js" type="text/javascript"></script>
        <script src="./scripts/cfgparser.js" type="text/javascript"></script>
        <title>Bezkontextové gramatiky</title>
    </head>
    <body>
        <div class="header" id="header">
            <div class="topLine">
            </div>
            <div class="headerAuthor">
                <div class="author" title="S nejasnostmi a problémy se obraťte na autora. (a) značí zavináč.">
                    Jonáš Ševčík: 255493(a)mail.muni.cz
                </div>
            </div>
            <div class="menuLine">
                <div class="innerMenu">
                    <ul class="menuServices">
                        <li><a href="http://arran.fi.muni.cz:8180/fja/" title="Regulární jazyky">Regulární jazyky</a></li>
                        <li><a href="./indexcfg.jsp" title="Bezkontextové gramatiky">Bezkontextové gramatiky</a></li>
                    </ul>
                    <ul class="menu"><li><a href="./help.html" title="Nápověda">Nápověda</a></li></ul>
                </div>
            </div>
        </div>
        <div class="page">
            <div class="content">
                <c:if test="${! empty ISString}">
                    <div class="window">
                        <h2 class="transformTitle">Řetězec odpovědníku:</h2>
                        <pre class="isString"><c:out value="${ISString}" /></pre>
                    </div>
                </c:if>
                <div class="window">
                    <c:forEach items="${windowData}" var="i">
                    <h2 class="transformTitle"><c:out value="${i.key}"/></h2>
                    <pre><c:out value="${i.value}"/></pre>
                    </c:forEach>
                    <form method="post" action="indexcfg.jsp">
                        <c:if test="${! empty inputData}">
                            <input type="hidden" name="inputData" value="<c:out value="${inputData}" />" />
                        </c:if>
                        <input value="Zpět" type="submit" class="buttonZ" />
                    </form>
                </div>
            </div>
        </div>
        <div class="bottomGradient">
            <div class="bottomLine">
                <a href="#header" class="bottomArrow"><img src="./style/toparrow.gif" title="Nahoru" /></a>
            </div>
        </div>
    </body>
</html>

