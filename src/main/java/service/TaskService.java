package service;

import config.DbUtil;
import model.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;

public class TaskService {
    public void addTask(Task task) {
        try (Session session = DbUtil.getSession()) {
            session.save(task);
        }
    }

    public Collection<Task> getAllTasks() {
        Collection<Task> users = null;
        Transaction transaction = null;
        try (Session session = DbUtil.getSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("Select u from Task u").getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            e.printStackTrace();

        }
        return users;
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
