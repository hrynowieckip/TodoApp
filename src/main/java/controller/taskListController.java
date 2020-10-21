package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.TaskList;
import service.TaskListService;
import currentList.CurrentList;

import java.io.IOException;


public class taskListController {
    TaskListService taskListService = new TaskListService();
    ObservableList<TaskList> items;

    @FXML
    private TextField nameTaskListField;
    @FXML
    private TableView<TaskList> taskListTableView;
    @FXML
    private TableColumn idTaskListColumn;
    @FXML
    private TableColumn nameTaskListColumn;
    @FXML
    private TableColumn createdOnTaskListColumn;

    @FXML
    public void initialize(){
        taskListTable();
    }

    public void taskListTable() {
        ObservableList<TaskList> taskListItem = FXCollections.observableArrayList (
                taskListService.getAllTaskList());

        if (!taskListItem.isEmpty()) {
            idTaskListColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameTaskListColumn.setCellValueFactory(new PropertyValueFactory<>("taskListName"));
            createdOnTaskListColumn.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
            taskListTableView.setItems(taskListItem);
        } else{taskListTableView.setPlaceholder(new Label("No rows to display"));}
    }

    @FXML
    public void addTaskListButton() {
        if(!nameTaskListField.getText().isEmpty()) {
            TaskList taskList = new TaskList(nameTaskListField.getText());
            taskListService.addTaskList(taskList);
            taskListTable();
            nameTaskListField.setText("");
        }
    }
    @FXML
    public void deleteTaskList(){
        TaskList taskList = taskListTableView.getSelectionModel().getSelectedItem();
        if(taskList != null) {
            taskListService.deleteTaskList(taskList);
            taskListTable();
        }
    }
    @FXML
    public void selectTaskListButton(ActionEvent actionEvent){
        TaskList taskList = taskListTableView.getSelectionModel().getSelectedItem();
        if(taskList != null){
            CurrentList.setId(taskList.getId());
            newWindow(actionEvent);
        }
    }

    public void newWindow(ActionEvent actionEvent) {
        Parent root;
        Scene scene;
        Stage stage = new Stage();
        try {
            root = FXMLLoader.load(getClass()
                    .getResource("/taskWindow.fxml"));

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
