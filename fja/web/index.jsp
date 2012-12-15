<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="example" scope="request" class="cz.muni.fi.fja.servlet.Examples"/>
<%! public String equal = "equal";%>   
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="Author" content="Bronislav Houdek">
  <title>Zpracovani dotazu pro Reg.jazyky</title>
  <link rel="stylesheet" type="text/css" href="css/index.css">
  <script language="JavaScript">
    function selEx(i) {
        location.replace("index.jsp?ex=" + i + "#priklad");
    }
  </script>
</head>
<body>
<center>
<h1>Vyhodnocovaci sluzba k regularnim jazykum</h1>
<h2><a href="indexcfg.jsp">Vyhodnocovaci sluzba k bezkontextovym gramatikam</a></h2>
<p>
  S dotazy nebo problemy se prosim obracejte na<br>
  <b>xsysel@seznam.cz</b> (Bronislav Houdek)<br>
</p>
  <a class="menu" href="list.jsp">Vlozene ulohy</a>, 
  <a class="menu" href="help.html">Napoveda</a>, 
  <a class="menu" href="news.html">Novinky 09-03-14 - 13:00</a>
  <br>
<hr>


<form method="get" action="<%= equal%>">
<h2>Simulace odpovedniku</h2>
<table border="0" cellpadding="6" cellspacing="0"><tbody>
  <tr>
    <th colspan="2" class=teacher>Jazyk spravne odpovedi + zpusob jeho popisu</th>
    <th rowspan="2"></th>
    <th class=teacher>Pozadavek na odpoved</th>
    <th rowspan="2"></th>
    <th class=student>Odpoved studenta</th>
  </tr>
  <tr>
    <jsp:include page = "WEB-INF/jspf/teacher.jsp"/>
    <jsp:include page = "WEB-INF/jspf/answer.jsp"/>
    <jsp:include page = "WEB-INF/jspf/student.jsp"/>
  </tr>
</tbody></table>
<jsp:include page = "WEB-INF/jspf/mod.jspf"/>
</form>
<hr>

<jsp:setProperty name="example" property="ex" value="0"/>
<jsp:setProperty name="example" property="ex"/>
<form method="get" action="<%= equal%>">
<a name="priklad"><h2>Priklady pouziti</h2></a>
<select name="ex" size="1">
<%     
  String[] exNames = example.getNames();
//  <c:set scope="page" var="exNames" value="${example.names}"/>
  for (int i = 0; i < example.getCount(); i++) {
      out.print("<option class=\"exSelect\" value=" + i + " onclick=\"selEx(" + i + ");\"");
      if (example.getEx() == i) {
          out.print("selected=\"selected\"");
      }
      out.println(">" + exNames[i] + "</option>");
  }
//  <c:forEach var="i" begin="0" end="${example.count-1}">
//      <option class="exSelect" value="${i}" onclick="selEx(${i});" <c:if test="${param.ex==i}">selected="selected"</c:if>>${exNames[i]}</option>
//  </c:forEach>
%>
</select><br><br>
<table width="70%"><tbody><tr><td>
<%=example.getText()/*  <c:out value="${example.text}"/>*/%>
</td></tr></tbody></table><br>
<table border="0" cellpadding="6" cellspacing="0"><tbody>
  <tr>
    <th colspan="2" class=teacher>Jazyk spravne odpovedi + zpusob jeho popisu</th>
    <th rowspan="2"></th>
    <th class=teacher>Pozadavek na odpoved</th>
    <th rowspan="2"></th>
    <th class=student>Odpoved studenta</th>
  </tr>
  <tr>
    <jsp:include page = "WEB-INF/jspf/teacher.jsp"/>
    <jsp:include page = "WEB-INF/jspf/answer.jsp"/>
    <jsp:include page = "WEB-INF/jspf/student.jsp"/>
  </tr>
</tbody></table>
<jsp:include page = "WEB-INF/jspf/mod.jspf"/>
</form>
<hr>

<jsp:setProperty name="example" property="ex" value="-1"/>
<form method="get" action="convert">
<h2>Preved model na</h2>
<table border="0" cellpadding="6" cellspacing="0"><tbody>
  <tr>
    <th colspan="2" class=teacher>Jazyk spravne odpovedi + zpusob jeho popisu</th>
    <th rowspan="2"></th>
    <th class=teacher>Pozadavek na odpoved</th>
  </tr>
  <tr>
    <jsp:include page = "WEB-INF/jspf/teacher.jsp"/>
    <jsp:include page = "WEB-INF/jspf/convert.jsp"/>
  </tr>
</tbody></table>
<jsp:include page = "WEB-INF/jspf/mod.jspf"/>
</form>    

<hr>
</center>
</body>
</html>
