package cz.muni.fi.fja.deposit;

public class TaskListImpl implements Task{
  private int hc;
  private String sInfo;
  private String tInfo;
  private String student;
  private String teacher;
  private String answer;
  
  public TaskListImpl() {
  }
  
  public TaskListImpl(String ti, String si, String t, String s, String a) throws DepositorException {
    tInfo = ti == null ? "NUL" : ti;
    sInfo = si == null ? "NUL" : si;
    teacher = t == null ? "" : t;
    student = s == null ? "" : s;
    answer = a == null ? "F" : a;
  }
  
  public void setSInfo(String si) throws DepositorException {
    throw new DepositorException("");
  }
  public String getSInfo() {
    return sInfo;
  }

  public void setTInfo(String ti) throws DepositorException {
    throw new DepositorException("");
  }
  public String getTInfo() {
    return tInfo;
  }

  public void setStudent(String s) throws DepositorException {
    throw new DepositorException("");
  }
  public String getStudent() {
    return student;
  }

  public void setTeacher(String t) throws DepositorException {
    throw new DepositorException("");
  }
  public String getTeacher() {
    return teacher;
  }

  public void setAnswer(String a) throws DepositorException {
    throw new DepositorException("");
  }
  public String getAnswer() {
    return answer;
  }
  public String getAnswerString() {
    return answer.equals("T") ? "TRUE" : "FALSE";
  }

  public String toString() {
    return "Task:" + tInfo + "-" + sInfo + ":" + getAnswerString();
  }
  
  public int hashCode() {
    if (hc == 0) {
      hc = answer.hashCode() + 1;
      hc = tInfo.hashCode() + 7*hc;
      hc = sInfo.hashCode() + 7*hc;
      hc = teacher.hashCode() + 7*hc;
      hc = student.hashCode() + 7*hc;
    }
    return hc;
  }
  
  public boolean equals(Object o) {
    if (!(o instanceof Task)) {
        return false;
    }
    Task t = (Task)o;
    if (getAnswer().equals(t.getAnswer()) &&
        getTInfo().equals(t.getTInfo()) && getSInfo().equals(t.getSInfo()) &&
        getTeacher().equals(t.getTeacher()) && getStudent().equals(t.getStudent())) {
        return true;
    }
    return false;
  }
}
