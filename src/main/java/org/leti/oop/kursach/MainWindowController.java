package org.leti.oop.kursach;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import java.io.IOException;
import javafx.scene.Parent;


import java.util.*;
import javafx.scene.Node;
import java.util.function.BiFunction;

import javax.swing.text.html.ImageView;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createTask_button;

    @FXML
    private Button deleteTask_button;

    @FXML
    private Button save_button;

    @FXML
    private Button exit_batton;

    @FXML
    private TextField search_textField;

    @FXML
    private Button sortTask_button;

    ObservableList<String> sortList = FXCollections.observableArrayList(
            "name", "task", "rating", "completness", "data");
    @FXML
    private ChoiceBox<String> choiseSort;

    @FXML
    private ImageView sortTask_image;

    @FXML
    private AnchorPane tasksField;

    @FXML
    private Label userName_label;

    private TaskContainerController taskContainerController;

    private Main main;

    private User currentUser = null;

    private int sortiration = 0;

    private List<Task> oldTaskList;

    void setMain(Main main){
        this.main = main;
    }

    void messageSave(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сообщение");
        alert.setHeaderText("Задачи успешно схоранены");
        alert.showAndWait();
    }


    private AnchorPane create_taskContainer(Task task){
        AnchorPane taskContainer = new AnchorPane();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/maket/taskContainer_v002.fxml"));
            taskContainer = loader.load();
            taskContainerController = loader.getController();

            taskContainerController.getTaskContainer().setUserData(task);

            return taskContainer;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    List<Task> getReverseTaskList(){
        List<Task> taskList = new ArrayList<>(currentUser.getTasks());
        Collections.reverse(taskList);
        return taskList;
    }


    @FXML
    void initialize() {

        currentUser = Data.getCurrentUser();

        userName_label.setText(currentUser.getName());

        oldTaskList = new ArrayList<>(currentUser.getTasks());


        for (Task task : getReverseTaskList()){
            tasksField.getChildren().add(create_taskContainer(task));
            taskContainerController.back_to_front();
        }


        createTask_button.setOnAction(actionEvent -> {
            Task task = new Task();
            currentUser.addTask(task);
            AnchorPane taskContainer = create_taskContainer(task);
            taskContainerController.addTaskData();
            tasksField.getChildren().add(0, taskContainer);
            taskContainerController.isSelected();
            taskContainerController.arrange(tasksField);
            Data.getInstance().saveData();

            oldTaskList.add(0,task);
        });

        deleteTask_button.setOnAction(actionEvent -> {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Delete?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                int len = tasksField.getChildren().size();
                for(int i=0; i < len; ++i){
                    AnchorPane taskContainer = (AnchorPane) tasksField.getChildren().get(i);
                    Task task = (Task) taskContainer.getUserData();
                    if (task.isSelect()) {
                        tasksField.getChildren().remove(taskContainer);
                        currentUser.getTasks().remove(task);
                        Data.getInstance().removeData(task);
                        len -= 1;
                        --i;
                        oldTaskList.remove(task);
                    }
                }
                if (!tasksField.getChildren().isEmpty()){
                    taskContainerController.arrange(tasksField);
                }
            }
        });

        save_button.setOnAction(actionEvent -> {
            Data.getInstance().saveData();
            Data.getInstance().savePDFReport(tasksField);
            messageSave();
        });

        choiseSort.setItems(sortList);
        choiseSort.setValue("name");
        sortTask_button.setOnAction(actionEvent -> {
            List<Task> tasklist = new ArrayList<>();
            for( Node node : tasksField.getChildren()){
                tasklist.add((Task)node.getUserData());
            }

            Comparator<Task> sortByAny = new Comparator<Task>() {
                @Override
                public int compare(Task t1, Task t2) {
                    String choise = choiseSort.getSelectionModel().getSelectedItem();
                    if (choise == "task")
                        return t1.getNote().getText().compareToIgnoreCase(
                                t2.getNote().getText());
                    else if (choise == "rating")
                        return t1.getRating() - t2.getRating();
                    else if (choise == "completness")
                        return t1.getCompletness() - t2.getCompletness();
                    else if (choise == "data"){
                        if  (t1.getDeadline() == null)
                            return 1;
                        if  (t2.getDeadline() == null)
                            return -1;
                        return t1.getDeadline().compareTo(t2.getDeadline());
                    }
                    else
                        return t1.getName().compareToIgnoreCase(t2.getName());
                }
            };

            if (sortiration == 0){
                oldTaskList = new ArrayList<>(tasklist);
                tasklist.sort(sortByAny);
                sortiration = 1;
            }
            else if (sortiration == 1){
                tasklist.sort(sortByAny);
                Collections.reverse(tasklist);
                sortiration = -1;
            }
            else if (sortiration == -1){
                tasklist = oldTaskList;
                sortiration = 0;
            }

            tasksField.getChildren().clear();
            for( Task task : tasklist){
                tasksField.getChildren().add(create_taskContainer(task));
                taskContainerController.back_to_front();
            }
        });

        search_textField.setOnKeyTyped(event -> {
            String request = search_textField.getText();

            for (Node node : tasksField.getChildren()){
                Task task = (Task)node.getUserData();
                boolean foundName = task.getName().contains(request);
                boolean foundNote = task.getNote().getText().contains(request);
                node.setVisible(foundName || foundNote);
            }
            taskContainerController.arrange(tasksField);
        });

        exit_batton.setOnAction(actionEvent -> {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Save?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                Data.getInstance().saveData();
                messageSave();
            }
            if (alert.getResult() != ButtonType.CANCEL)
                main.stageAuthenticationWindow();
        });
    }


}


