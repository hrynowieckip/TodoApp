package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.TaskList;
import service.TaskListService;


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
        if(taskList != null)
        taskListService.deleteTaskList(taskList);
        taskListTable();
    }
}
