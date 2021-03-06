package service;

import config.DbUtil;
import currentList.CurrentList;
import model.Task;
import model.TaskList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;

public class TaskService {
    public void addTask(Task task) {
        try (Session session = DbUtil.getSession()) {
            session.save(task);
        }
    }

    public void updateTask(Task task) {
        Transaction transaction = null;
        try (Session session = DbUtil.getSession()) {
            transaction = session.beginTransaction();
            session.update(task);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            e.printStackTrace();

        }
    }

    public Collection<Task> getAllTasksFromCurrentTaskList() {
        Collection<Task> tasks = null;
        Transaction transaction = null;
        try (Session session = DbUtil.getSession()) {
            transaction = session.beginTransaction();
            tasks = session.createQuery("SELECT q FROM Task q WHERE q.todosId =" + CurrentList.getId(), Task.class).getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return tasks;
    }

    public Collection<Task> getAllTasksForTaskList(TaskList taskList) {
        Collection<Task> tasks = null;
        Transaction transaction = null;
        try (Session session = DbUtil.getSession()) {
            transaction = session.beginTransaction();
            tasks = session.createQuery("SELECT q FROM Task q WHERE q.todosId =" + taskList.getId(), Task.class).getResultList();
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
