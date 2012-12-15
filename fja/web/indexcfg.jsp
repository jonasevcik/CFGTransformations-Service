<%-- 
    Document   : index
    Created on : 13.3.2009, 11:05:04
    Author     : NICKT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="utf-8"?>
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
                        <li><a href="./index.jsp" title="Regulární jazyky">Regulární jazyky</a></li>
                        <li><a href="./indexcfg.jsp" title="Bezkontextové gramatiky">Bezkontextové gramatiky</a></li>
                    </ul>
                    <ul class="menu"><li><a href="./helpcfg.html" title="Nápověda">Nápověda</a></li></ul>
                </div>
            </div>
        </div>
        <div class="page">
            <div class="content">
                <c:if test="${! empty error}">
                    <div class="alertWindow">
                        <div class="errorMessage">
                            <c:out value="${error}" />
                        </div>
                    </div>
                </c:if>
                <noscript>
                    <div class="alertWindow">
                        <div class="errorMessage">
                            JavaScript není povolen! Pro plnou funkčnost jej prosím zapněte.
                        </div>
                    </div>
                </noscript>
                <div class="window">
                    <h2 class="transformTitle">Převeď:</h2>
                    <form method="post" action="convertcfg" name="convert">
                        <textarea id="convert" name="inputData" cols="25" rows="12" title="Zde zadejte gramatiku k převodu. Např. S -> a | A, A -> b"><c:if test="${(! empty inputData) && empty error }"><c:out value="${inputData}" /></c:if><% if (request.getParameter("inputData") != null) {out.print(request.getParameter("inputData"));} %></textarea>
                        <div id="convert-error" class="parser_error" title="Nápověda syntaxe.">Zde se zobrazuje nápověda syntaxe. Začněte psát do formuláře nahoře.</div>
                        <ul class="list">
                            <li><input name="stud" value="NE1" id="NE1" type="radio" checked="checked" /><label for="NE1"> <b>NE1</b> - Odstranit nenormované symboly</label></li>
                            <li><input name="stud" value="NE2" id="NE2" type="radio" /><label for="NE2"> <b>NE2</b> - Odstranit nedosažitelné symboly</label></li>
                            <li><input name="stud" value="RED" id="RED" type="radio" /><label for="RED"> <b>RED</b> - Převést na redukovanou CFG</label></li>
                            <li><input name="stud" value="EPS" id="EPS" type="radio" /><label for="EPS"> <b>EPS</b> - Odstranit epsilon kroky</label></li>
                            <li><input name="stud" value="SRF" id="SRF" type="radio" /><label for="SRF"> <b>SRF</b> - Odstranit jednoduchá pravidla</label></li>
                            <li><input name="stud" value="PRO" id="PRO" type="radio" /><label for="PRO"> <b>PRO</b> - Převést na vlastní CFG</label></li>
                            <li><input name="stud" value="CNF" id="CNF" type="radio" /><label for="CNF"> <b>CNF</b> - Převést do CNF</label></li>
                            <li><input name="stud" value="RLR" id="RLR" type="radio" /><label for="RLR"> <b>RLR</b> - Odstranit levou rekurzi</label></li>
                            <li><input name="stud" value="GNF" id="GNF" type="radio" /><label for="GNF"> <b>GNF</b> - Převést do GNF</label></li>
                        </ul>
                        <div class="modes">
                            <select name="mode" title="Zvolte si mód odpovědi.">
                                <option value="normal" title="V tomto módu je zobrazen pouze výsledný model gramatiky">Normální mód</option>
                                <option value="verbose" title="V tomto módu je zobrazena posloupnost transformací gramatiky až do její finální formy.">Detailní mód</option>
                            </select>
                            <input type="checkbox" id ="generateISString" name="generateISString" value="true" class="check" /><label for="generateISString" title="V odpovědi zobrazí i řetězec pro odpovědník.">Vygenerovat řetězec pro odpovědník</label>
                            <input value="Převeď" type="submit" class="button" title="Převede gramatiku do požadované formy." />
                        </div>
                    </form>
                </div>
                <c:if test="${! empty error2}">
                    <div class="alertWindow">
                        <div class="errorMessage">
                            <c:out value="${error2}" />
                        </div>
                    </div>
                </c:if>
                <div class="window">
                    <h2 class="transformTitle">Simulace odpovědníku:</h2>
                    <div class="innerSpan">
                        <div class="left">Zde vyplňte řešení studenta.</div>
                        <div class="middle" title="Nezadávejte vyřešení zadání, poněvadž by mohlo dojít chybnému vyhodnocení.">Zde vyplňte nevyřešené zadání úkolu. (Jinak může dojít k chybnému vyhodnocení)</div>
                        <div class="right">Zvolte jaký převod se po studentovi požadoval.</div>
                    </div>
                    <form method="post" action="evaluatecfg" name="convert">
                        <textarea id="evaluate-stud" name="studentData" cols="25" rows="12" title="Model studenta. Zadejte gramatiku např. S -> a | A, A -> b"><c:if test="${(! empty studentData) && empty error2}"><c:out value="${studentData}" /></c:if><% if (request.getParameter("studentData") != null) {out.print(request.getParameter("studentData"));} %></textarea>
                        <textarea id="evaluate-teach" name="teacherData" cols="25" rows="12" title="Model učitele. Zde vložte zadání úkolu např. S -> a | A, A -> b"><c:if test="${(! empty teacherData) && empty error2}"><c:out value="${teacherData}" /></c:if><% if (request.getParameter("teacherData") != null) {out.print(request.getParameter("teacherData"));} %></textarea>
                        <ul class="list2">
                            <li><input name="stud" value="NE1" id="NE12" type="radio" checked="checked" /><label for="NE12"> <b>NE1</b> - Odstranit nenormované symboly</label></li>
                            <li><input name="stud" value="NE2" id="NE22" type="radio" /><label for="NE22"> <b>NE2</b> - Odstranit nedosažitelné symboly</label></li>
                            <li><input name="stud" value="RED" id="RED2" type="radio" /><label for="RED2"> <b>RED</b> - Převést na redukovanou CFG</label></li>
                            <li><input name="stud" value="EPS" id="EPS2" type="radio" /><label for="EPS2"> <b>EPS</b> - Odstranit epsilon kroky</label></li>
                            <li><input name="stud" value="SRF" id="SRF2" type="radio" /><label for="SRF2"> <b>SRF</b> - Odstranit jednoduchá pravidla</label></li>
                            <li><input name="stud" value="PRO" id="PRO2" type="radio" /><label for="PRO2"> <b>PRO</b> - Převést na vlastní CFG</label></li>
                            <li><input name="stud" value="CNF" id="CNF2" type="radio" /><label for="CNF2"> <b>CNF</b> - Převést do CNF</label></li>
                            <li><input name="stud" value="RLR" id="RLR2" type="radio" /><label for="RLR2"> <b>RLR</b> - Odstranit levou rekurzi</label></li>
                            <li><input name="stud" value="GNF" id="GNF2" type="radio" /><label for="GNF2"> <b>GNF</b> - Převést do GNF</label></li>
                        </ul>
                        <div class="modes2">
                            <select name="mode" title="Zvolte si mód odpovědi.">
                                <option value="normal" title="V tomto módu je zobrazen pouze výsledný model gramatiky">Normální mód</option>
                                <option value="simple" title="V tomto módu je v závislosti na rovnosti gramatik zobrazena odpověď True/False.">True/False mód</option>
                            </select>
                            <input value="Porovnej" type="submit" class="button" title="Porovná zadané gramatiky." />
                        </div>
                        <div id="evaluate-stud-error" class="parser_error" title="Nápověda syntaxe k formuláři pro odpověď studenta.">Zde se zobrazuje nápověda syntaxe pro model gramatiky studenta. Začněte psát do formuláře vlevo nahoře.</div>
                        <div id="evaluate-teach-error" class="parser_error" title="Nápověda syntaxe k formuláři pro modelovou odpověď učitele.">Zde se zobrazuje nápověda syntaxe pro model gramatiky učitele. Začněte psát do formuláře vpravo nahoře.</div>
                    </form>
                </div>
            </div>
        </div>
        <div class="bottomGradient">
            <div class="bottomLine">
                <a href="#header" class="bottomArrow"><img src="./style/toparrow.gif" title="Nahoru" /></a>
            </div>
        </div>
        <script type="text/javascript">
            register('convert', Parser.parse, document.getElementById('convert'));
            register('evaluate-stud', Parser.parse, document.getElementById('evaluate-stud'));
            register('evaluate-teach', Parser.parse, document.getElementById('evaluate-teach'));
        </script>
    </body>
</html>
