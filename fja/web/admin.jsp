<%@page contentType="text/html" pageEncoding="UTF-8"
        import="cz.muni.fi.fja.deposit.Depositor"
        import="cz.muni.fi.fja.servlet.Equal"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%Depositor depIS = Equal.getDepositorTasksFromIS();
  Depositor depWeb = Equal.getDepositorTasksFromWebInterface();
  String tasks = request.getParameter("tasks");
  if (tasks == null) {
    tasks = "";
  }
  {
    if (request.getParameter("reset") != null) {
      if (tasks.equals("IS") || tasks.equals("all")) {
        depIS.resetDepositor();
      }
      if(tasks.equals("web") || tasks.equals("all")) {
        depWeb.resetDepositor();
      }
    } else if (request.getParameter("set") != null) {
      try {
        if ("yes".equals(request.getParameter("saveIS"))) {
            depIS.savingPermited();
        } else {
            depIS.savingDisabled();
        }
        if ("yes".equals(request.getParameter("saveWeb"))) {
            depWeb.savingPermited();
        } else {
            depWeb.savingDisabled();
        }
      } catch (NumberFormatException nfe) {
        // hold smula Machale
      }
    }
  }
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Administrace</title>
  <link rel="stylesheet" type="text/css" href="css/admin.css">
</head>
<body>
<center>
<h1>Administrace vkladanych uloh</h1> 
    
<form method="get" action="admin.jsp">
<table> 
  <tr>
    <td colspan="2">Pocet ukolu celkem: <b><%= depIS.getCountOfTasks() + depWeb.getCountOfTasks() %></b></td>
  </tr>
  <tr height="10px">
    <td colspan="2"></td>
  </tr>
  <tr>
    <td class="right">Ukladat ulohy z ISu: <input type=checkbox name="saveIS" value="yes" <%= depIS.isSavingPermited() ? "checked" : "" %>></td>
    <td class="left">Pocet ukolu pres IS: <b><%= depIS.getCountOfTasks() %></b></td>
  </tr>
  <tr>
    <td class="right">Ukladat ulohy z Webu: <input type=checkbox name="saveWeb" value="yes" <%= depWeb.isSavingPermited() ? "checked" : ""%>></td>
    <td class="left">Pocet ukolu pres Web: <b><%= depWeb.getCountOfTasks() %></b></td>
  </tr>
  <tr height="20px">
    <td colspan="2"></td>
  </tr>
  <tr>
    <td colspan="2" class="bold">Zpracovavej pozadavky pouze od is.muni.cz<input type=checkbox name="showWeb" value="yes" <%= depWeb.isSavingPermited() ? "checked" : ""%> disabled></td>
  </tr>
  <tr height="10px">
    <td colspan="2"></td>
  </tr>
  <tr>
    <td colspan="2"><input type=submit value="Nastav" name="set"></td>
  </tr>    
</table>
<br>
<br>
<table> 
  <tr>
    <td colspan="2">
      <label>vsechno(<input type=radio value="all" name="tasks" <%= tasks.equals("all") ? "checked" : "" %>>)</label> / 
      <label>ulohy z ISu(<input type=radio value="IS" name="tasks" <%= tasks.equals("IS") ? "checked" : "" %>>)</label> / 
      <label>ulohy z web.rozhrani(<input type=radio value="web" name="tasks" <%= tasks.equals("web") ? "checked" : "" %>>)</label>
    </td>
  </tr>    
  <tr>
    <td colspan="2"><input type=submit value="Smaz ulohy" name="reset"></td>
  </tr>    
  <tr>
    <td><a href="export?task=IS">Exportuj IS</a></td>
    <td><a href="export?task=web">Exportuj Web</a></td>
  </tr> 
</table>
</form>
</center>
</body>
</html>
