package cz.muni.fi.fja.deposit;

import java.util.Collection;

public interface Depositor {

  void savingDisabled();
  void savingPermited();
  boolean isSavingPermited();
  
  void insertTask(String ti, String si, String t, String s, String a) throws DepositorException;

  void insertTask(Task e) throws DepositorException;

  Collection<Task> getListOfElements() throws DepositorException;

  Task[] getArrayOfElements() throws DepositorException;

  int getCountOfTasks();

  void resetDepositor();

  void setCounter(int i);
}
