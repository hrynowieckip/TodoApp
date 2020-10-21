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
    TaskListService taskListService= new TaskListService();
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
            completedTaskColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));
            nameTaskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            createdOnTaskColumn.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
            tasksTableView.setItems(tasksItem);
        } else{tasksTableView.setPlaceholder(new Label("No rows to display"));}
    }

    @FXML
    public void addTaskButton() {
        if(!nameTaskField.getText().isEmpty()) {
            TaskList taskListById = taskListService.findTaskListById(CurrentList.getId());
            Task task = new Task(nameTaskField.getText(), taskListById, CurrentList.getId());

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
    @FXML
    public void selectTaskButton(){

    }
    @FXML
    public void backTaskButton(ActionEvent actionEvent){
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
            scene = new Scene(root,600,800);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
