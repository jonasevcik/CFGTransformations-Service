package cz.muni.fi.fja.deposit;

public interface Task {
  void setSInfo(String si) throws DepositorException;
  String getSInfo();
  void setTInfo(String ti) throws DepositorException;
  String getTInfo();

  void setStudent(String s) throws DepositorException;
  String getStudent();
  void setTeacher(String t) throws DepositorException;
  String getTeacher();

  void setAnswer(String a) throws DepositorException;
  String getAnswer();
  String getAnswerString();
}
