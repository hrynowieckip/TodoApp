package service;

import config.DbUtil;
import model.TaskList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;

public class TaskListService {
    public void addTaskList(TaskList taskList) {
        try (Session session = DbUtil.getSession()) {
            session.save(taskList);
        }
    }

    public Collection<TaskList> getAllTaskList() {
        Collection<TaskList> users = null;
        Transaction transaction = null;
        try (Session session = DbUtil.getSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("Select u from TaskList u", TaskList.class).getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            e.printStackTrace();

        }
        return users;
    }

    public void deleteTaskList(TaskList taskList){
        Transaction transaction = null;
        try (Session session = DbUtil.getSession()) {
            transaction = session.beginTransaction();
            session.delete(taskList);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            e.printStackTrace();

        }
    }
    public TaskList findTaskListById(Long id){
        TaskList taskList;
        try(Session session= DbUtil.getSession()){
            taskList= (TaskList) session.byId(String.valueOf(id));
        }
        return taskList;
    }
}
