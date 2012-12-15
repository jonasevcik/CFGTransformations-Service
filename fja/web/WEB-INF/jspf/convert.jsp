<%
    cz.muni.fi.fja.servlet.Examples example = (cz.muni.fi.fja.servlet.Examples)request.getAttribute("example");
%>
<td class="teacher">
    <label><input name="stud" value="MIC" type="radio" checked="checked"><b>MIC</b> - minimalni kanonicky DFA</label><br>
    <label><input name="stud" value="MIN" type="radio"<%
        if (example.getStudentType().equals("MIN")) {out.print(" checked=\"checked\"");}
        //<c:if test="${example.studentType=='MIN'}"> checked="checked"</c:if>%>><b>MIN</b> - minimalni DFA</label><br>
    <label><input name="stud" value="CAN" type="radio"<%
        if (example.getStudentType().equals("CAN")) {out.print(" checked=\"checked\"");}
        //<c:if test="${example.studentType=='CAN'}"> checked="checked"</c:if>%>><b>CAN</b> - kanonicky DFA</label><br>
    <label><input name="stud" value="DFA" type="radio"<%
        if (example.getStudentType().equals("DFA")) {out.print(" checked=\"checked\"");}
        //<c:if test="${example.studentType=='DFA'}"> checked="checked"</c:if>%>><b>DFA</b></label><br>
    <label><input name="stud" value="NFA" type="radio"<%
        if (example.getStudentType().equals("NFA")) {out.print(" checked=\"checked\"");}
        //<c:if test="${example.studentType=='NFA'}"> checked="checked"</c:if>%>><b>NFA</b></label><br>
    <label><input name="stud" value="EFA" type="radio"<%
        if (example.getStudentType().equals("EFA")) {out.print(" checked=\"checked\"");}
        //<c:if test="${example.studentType=='EFA'}"> checked="checked"</c:if>%>><b>EFA</b> - NFA s epsilon kroky</label><br>
 </td>
