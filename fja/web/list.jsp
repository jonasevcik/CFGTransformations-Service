<%@page contentType="text/html" pageEncoding="UTF-8"
    import="cz.muni.fi.fja.deposit.Depositor"
    import="cz.muni.fi.fja.deposit.Task"
    import="java.util.Iterator"
    import="cz.muni.fi.fja.servlet.Equal"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
  Depositor depIS = Equal.getDepositorTasksFromIS();
  Depositor depWeb = Equal.getDepositorTasksFromWebInterface();
  String showList = request.getParameter("showList");
  if (showList == null || !(showList.equals("IS") || showList.equals("web"))) {
    showList = "not";
  }
%>

<html>
<head>
  <title>List of Examples</title>
  <link rel="stylesheet" type="text/css" href="css/list.css">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<center>
<h1>Historie vlozenych uloh</h1>
<p>
  Nejsou zde archivovany vsechny ulohy - ukladaji se pouze ty, ktere se zadaji
  pres formular patrici k teto aplikaci. Ulohy jsou ukladany pouze docasne,
  pricemz jednou za cas budou exportovany pryc a zde vymazany.
</p>
<form method=get action="list.jsp">    
<table id="info">
  <tr>
    <td class="right">Pocet ukolu celkem:</td>
    <td class="left"><%= depIS.getCountOfTasks() + depWeb.getCountOfTasks() %></td>
  </tr>
  <tr>
    <td class="right">Pocet ukolu pres IS:</td>
    <td class="left"><%= depIS.getCountOfTasks()%></td>
  </tr>
  <tr>
    <td class="right">Pocet ukolu pres Webove rozhrani:</td>
    <td class="left"><%= depWeb.getCountOfTasks()%></td>
  </tr>
  <tr>
    <td class="right">Zobrazit seznam ukolu:</td>
    <td class="left">
      <label>z Webu(<input type=radio name=showList value="web"<%= showList.equals("web") ? "checked" : "" %>>)</label> / 
      <label>z ISu(<input type=radio name=showList value="IS"<%= showList.equals("IS") ? "checked" : "" %>>)</label> / 
      <label>nezobrazit(<input type=radio name=showList value="not" <%= showList.equals("not") ? "checked" : "" %>>)</label>
    </td>
  </tr>
  <tr>
    <td colspan=2><input type=submit value="Refresh" name="show"></td>
  </tr>    
</table>
</form>    
    
<%if (!showList.equals("not")) { %>
  <table id="list">
    <tr>
      <th>ID</th>
      <th>Zadani</th>
      <th>Odpoved</th>
      <th>Model ucitele</th>
      <th>Model studenta</th>
    </tr>
    <%Depositor depositor = showList.equals("web") ? depWeb : depIS;
      Task[] tasksArray = depositor.getArrayOfElements();
      for (int counter = 0; counter < tasksArray.length; counter++) {
      	Task e = tasksArray[counter];
    %>
      <tr>
        <td> <%= "" + counter %> </td>
        <td class = task><pre> <%= e.getTInfo() + "-" + e.getSInfo() %> </pre></td>
        <td class = answer><pre> <%= e.getAnswerString() %> </pre></td>
        <td><pre> <%= e.getTeacher() %> </pre></td>
        <td><pre> <%= e.getStudent() %> </pre></td>
      </tr>
    <%} %>
  </table>
<%} %>

</center>    
</body>
</html>
