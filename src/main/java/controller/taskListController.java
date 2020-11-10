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
        taskListTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        taskListTable();
    }

    public void taskListTable() {
        ObservableList<TaskList> taskListItem = FXCollections.observableArrayList (
                taskListService.getAllTaskList());

        if (!taskListItem.isEmpty()) {
            idTaskListColumn.setVisible(true);
            nameTaskListColumn.setVisible(true);
            createdOnTaskListColumn.setVisible(true);

            idTaskListColumn.prefWidthProperty().bind(taskListTableView.widthProperty().multiply(0.1));
            nameTaskListColumn.prefWidthProperty().bind(taskListTableView.widthProperty().multiply(0.73));
            createdOnTaskListColumn.prefWidthProperty().bind(taskListTableView.widthProperty().multiply(0.17));

            idTaskListColumn.setResizable(false);
            nameTaskListColumn.setResizable(false);
            createdOnTaskListColumn.setResizable(false);

            idTaskListColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameTaskListColumn.setCellValueFactory(new PropertyValueFactory<>("taskListName"));
            createdOnTaskListColumn.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
            taskListTableView.setItems(taskListItem);
        } else{
            idTaskListColumn.setVisible(false);
            nameTaskListColumn.setVisible(false);
            createdOnTaskListColumn.setVisible(false);
            taskListTableView.setPlaceholder(new Label("Nie masz jeszcze żadnej listy zadań"));
        }
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
        enterTaskList(actionEvent, taskList);
    }

    public void enterTaskList(ActionEvent actionEvent, TaskList taskList) {
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
            scene = new Scene(root,585,800);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            // Hide this current window
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
