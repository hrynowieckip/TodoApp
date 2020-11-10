package service;

import config.DbUtil;
import model.Task;
import model.TaskList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;

public class TaskListService {
    private TaskService taskService = new TaskService();
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
        Collection<Task> allTasksForTaskList = taskService.getAllTasksForTaskList(taskList);
        if(allTasksForTaskList.isEmpty()){
            deleteEmptyTaskList(taskList);
        } else {
            for(Task e: allTasksForTaskList){
                taskService.deleteTask(e);
            }
            deleteEmptyTaskList(taskList);
        }
    }

    public void deleteEmptyTaskList(TaskList taskList){
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
            taskList=session.get(TaskList.class, id);
        }
        return taskList;
    }
}
