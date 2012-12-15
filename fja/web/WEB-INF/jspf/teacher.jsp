<%
    cz.muni.fi.fja.servlet.Examples example = (cz.muni.fi.fja.servlet.Examples)request.getAttribute("example");
%>
<td class="teacher">
    <textarea name="t" cols="25" rows="12"><%=example.getTeacher()/*<c:out value="${example.teacher}"/>*/%></textarea>
</td><td class="teacher">
    <label><input name="teach" value="DFA" type="radio"<% 
        if (example.getTeacherType().equals("DFA")) {out.print(" checked=\"checked\"");}
        //<c:if test="${example.teacherType=='DFA'}"> checked="checked"</c:if>%>><b>DFA</b></label><br>
    <label><input name="teach" value="EFA" type="radio"<% 
        if (example.getTeacherType().equals("EFA")) {out.print(" checked=\"checked\"");}
        //<c:if test="${example.teacherType=='EFA'}"> checked="checked"</c:if>%>><b>EFA</b> - NFA s epsilon kroky</label><br>
    <label><input name="teach" value="GRA" type="radio"<%
        if (example.getTeacherType().equals("GRA")) {out.print(" checked=\"checked\"");}
        //<c:if test="${example.teacherType=='GRA'}"> checked="checked"</c:if>%>><b>GRA</b> - gramatika</label><br>
    <label><input name="teach" value="REG" type="radio"<%
        if (example.getTeacherType().equals("REG")) {out.print(" checked=\"checked\"");}
        //<c:if test="${example.teacherType=='REG'}"> checked="checked"</c:if>%>><b>REG</b> - RE vyraz</label><br>
</td>
