package service;

import config.DbUtil;
import model.Task;
import model.TaskList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.List;

public class TaskListService {
    public void addTaskList(TaskList taskList) {
        try (Session session = DbUtil.getSession()) {
            session.save(taskList);
        }
    }    public Collection<TaskList> getAllTaskList() {
        Collection<TaskList> users = null;
        Transaction transaction = null;
        try (Session session = DbUtil.getSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("Select u from TaskList u").getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            e.printStackTrace();

        }
        return users;
        }
}
