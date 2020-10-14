package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Task;
import model.TaskList;
import service.TaskListService;
import service.TaskService;

public class taskController {
    TaskService taskService = new TaskService();
    ObservableList<Task> items;

    @FXML
    private TextField nameTaskField;
    @FXML
    private TableView<Task> tasksTableView;
    @FXML
    private TableColumn completedTaskColumn;
    @FXML
    private TableColumn nameTaskColumn;
    @FXML
    private TableColumn createdOnTaskColumn;

    @FXML
    public void initialize(){
        taskTable();
    }

    public void taskTable() {
        ObservableList<Task> tasksItem = FXCollections.observableArrayList (
                taskService.getAllTasks());

        if (!tasksItem.isEmpty()) {
            completedTaskColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameTaskColumn.setCellValueFactory(new PropertyValueFactory<>("taskListName"));
            createdOnTaskColumn.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
            tasksTableView.setItems(tasksItem);
        } else{tasksTableView.setPlaceholder(new Label("No rows to display"));}
    }

    @FXML
    public void addTaskButton() {
        if(!nameTaskField.getText().isEmpty()) {
            Task task = new Task(nameTaskField.getText());
            taskService.addTask(task);
            taskTable();
            nameTaskField.setText("");
        }
    }
    @FXML
    public void deleteTaskButton(){
        Task task = tasksTableView.getSelectionModel().getSelectedItem();
        if(task != null)
            taskService.deleteTask(task);
        taskTable();
    }
}
