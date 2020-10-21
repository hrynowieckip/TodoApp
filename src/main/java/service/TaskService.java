package service;

import config.DbUtil;
import model.Task;
import model.TaskList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import currentList.CurrentList;

import java.util.Collection;

public class TaskService {
    public void addTask(Task task) {
        try (Session session = DbUtil.getSession()) {
            session.save(task);
        }
    }

    public Collection<Task> getAllTasks() {
        Collection<Task> tasks = null;
        Transaction transaction = null;
        try (Session session = DbUtil.getSession()) {
            transaction = session.beginTransaction();
            tasks = session.createQuery("select c from Task as c", Task.class).getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            e.printStackTrace();

        }
        return tasks;
    }

    public void deleteTask(Task task) {
        Transaction transaction = null;
        try (Session session = DbUtil.getSession()) {
            transaction = session.beginTransaction();
            session.delete(task);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            e.printStackTrace();

        }
    }
}
