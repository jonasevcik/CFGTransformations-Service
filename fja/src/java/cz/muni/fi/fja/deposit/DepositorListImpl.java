package cz.muni.fi.fja.deposit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepositorListImpl implements Depositor {
  private int countOfTasks;
  private List<Task> listOfTasks;
  private Set<Task> setOfTasks;
  private boolean saving;

  
  public DepositorListImpl() {
      countOfTasks = 0;
      listOfTasks = new ArrayList<Task>();
      setOfTasks = new HashSet<Task>();
      saving = true;
  }
  
  public void insertTask(String ti, String si, String t, String s, String a) throws DepositorException {
      if (saving) {
          Task e = new TaskListImpl(ti, si, t, s, a);
          if (setOfTasks.add(e)) {
              listOfTasks.add(e);
          }
      }
      countOfTasks++;
  }
  
  public void insertTask(Task e) throws DepositorException {
      if (e == null) {
          throw new DepositorException("Illegal argument. Vkladana uloha ma hodnotu null.");
      } else {
          insertTask(e.getTInfo(), e.getSInfo(), e.getTeacher(), e.getStudent(), e.getAnswer());
      }
  }
  public Collection<Task> getListOfElements() throws DepositorException {
      return listOfTasks;
  }

  public Task[] getArrayOfElements() throws DepositorException {
    return listOfTasks.toArray(new Task[listOfTasks.size()]);
  }
  
  public int getCountOfTasks() {
      return countOfTasks;
  }
  
  public void resetDepositor() {
      setCounter(0);
      listOfTasks.clear();
  }

  public void setCounter(int i) {
      countOfTasks = i;
  }
  
  public void savingDisabled() {
      saving = false;
  }
  
  public void savingPermited() {
      saving = true;
  }
  
  public boolean isSavingPermited() {
      return saving;
  }
}
