package controller;

import currentList.CurrentList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Task;
import model.TaskList;
import service.TaskListService;
import service.TaskService;

import java.io.IOException;

public class taskController {
    TaskService taskService = new TaskService();
    TaskListService taskListService = new TaskListService();

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
    public void initialize() {
        tasksTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        taskTable();
    }

    public void taskTable() {
        ObservableList<Task> tasksItem = FXCollections.observableArrayList(
                taskService.getAllTasksFromCurrentTaskList());
        if (!tasksItem.isEmpty()) {
            completedTaskColumn.setVisible(true);
            nameTaskColumn.setVisible(true);
            createdOnTaskColumn.setVisible(true);

            completedTaskColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.1));
            createdOnTaskColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.17));
            nameTaskColumn.prefWidthProperty().bind(tasksTableView.widthProperty().multiply(0.73));

            completedTaskColumn.setResizable(false);
            createdOnTaskColumn.setResizable(false);
            nameTaskColumn.setResizable(false);

            completedTaskColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));
            nameTaskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            createdOnTaskColumn.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
            tasksTableView.setItems(tasksItem);
        } else {
            completedTaskColumn.setVisible(false);
            nameTaskColumn.setVisible(false);
            createdOnTaskColumn.setVisible(false);
            tasksTableView.setPlaceholder(new Label("There are no tasks in this list."));
        }
    }

    @FXML
    public void addTaskButton() {
        if (!nameTaskField.getText().isEmpty()) {
            TaskList taskListById = taskListService.findTaskListById(CurrentList.getId());
            Task task = new Task(nameTaskField.getText(), taskListById, CurrentList.getId(), false);

            taskService.addTask(task);
            taskTable();
            nameTaskField.setText("");
        }
    }

    @FXML
    public void deleteTaskButton() {
        Task task = tasksTableView.getSelectionModel().getSelectedItem();
        if (task != null) {
            taskService.deleteTask(task);
        }
        taskTable();
    }

    @FXML
    public void selectTaskButton() {
        Task task = tasksTableView.getSelectionModel().getSelectedItem();
        if (task != null) {
            task.setCompleted(!task.getCompleted());
            taskService.updateTask(task);
        }
        taskTable();
    }

    @FXML
    public void backTaskButton(ActionEvent actionEvent) {
        newWindow(actionEvent);
    }

    public void newWindow(ActionEvent actionEvent) {
        Parent root;
        Scene scene;
        Stage stage = new Stage();
        try {
            root = FXMLLoader.load(getClass()
                    .getResource("/taskListWindow.fxml"));
            stage.setTitle("TodoApp");
            scene = new Scene(root, 585, 800);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            // Hide this current window
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
