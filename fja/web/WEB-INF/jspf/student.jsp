<%
    cz.muni.fi.fja.servlet.Examples example = (cz.muni.fi.fja.servlet.Examples)request.getAttribute("example");
%>
<td class="student">
    <textarea name="s" cols="25" rows="12"><%=example.getStudent()/*<c:out value="${example.student}"/>*/%></textarea>
</td>