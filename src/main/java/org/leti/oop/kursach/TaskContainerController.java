package org.leti.oop.kursach;

import java.util.*;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import javafx.scene.input.*;
import javafx.util.StringConverter;

import java.time.format.DateTimeParseException;
import java.time.LocalDate;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class TaskContainerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox buttonTime;

    @FXML
    private Button nameTask;

    @FXML
    private TextField nameTask_textField;

    @FXML
    private ProgressBar completnessBar;

    @FXML
    private Slider completnessSlider;

    @FXML
    private Button openSettings_button;

    @FXML
    private TextArea noteTask;

    @FXML
    private Button openTask_button;

    @FXML
    private CheckBox didTask_button;

    @FXML
    private Pane grayPane;

    @FXML
    private AnchorPane taskContainer;

    ObservableList<String> list_for_stars = FXCollections.observableArrayList("★", "★★", "★★★", "★★★★", "★★★★★");
    @FXML
    private ChoiceBox<String> list_of_stars;

    @FXML
    private ImageView star1;

    @FXML
    private ImageView star2;

    @FXML
    private ImageView star3;

    @FXML
    private ImageView star4;

    @FXML
    private ImageView star5;

    @FXML
    private DatePicker dataField;

    @FXML
    private CheckBox notice_CheckBox;

    @FXML
    private ImageView notice_img;

    private Task task;

    public AnchorPane getTaskContainer() {
        return taskContainer;
    }

    public void setTaskContainer(AnchorPane taskContainer) {
        this.taskContainer = taskContainer;
    }

    public Button getNameTask() {
        return nameTask;
    }

    public Pane getGrayPane() {
        return grayPane;
    }

    public Task getTask() {
        return task;
    }

    public void arrange(AnchorPane tasksField){
        double lastLayoutY = 10;
        for (Node node : tasksField.getChildren()){
            AnchorPane task = (AnchorPane) node;
            task.setLayoutY(lastLayoutY);
            lastLayoutY += (task.getPrefHeight() + 10)
                    * (node.isVisible() ? 1 : 0);
        }
    }
    public void arrange(){
        double lastLayoutY = 10;
        for (Node node : ((AnchorPane)taskContainer.getParent()).getChildren()){
            AnchorPane task = (AnchorPane) node;
            task.setLayoutY(lastLayoutY);
            lastLayoutY += (task.getPrefHeight() + 10)
                    * (node.isVisible() ? 1 : 0);
        }
    }

    void isSelected(){
        taskContainer.setStyle( taskContainer.getStyle() + ";" +
                "-fx-border-radius: 40" + ";" +
                "-fx-border-width: 1" + ";" +
                "-fx-border-color: #64BCFF" + ";" +
                "-fx-effect: dropshadow(gaussian, #0095ff, 10, 0, 0, 0)");
        Task task = (Task) taskContainer.getUserData();
        task.setSelect(true);
        taskContainer.setUserData(task);
    }

    void noSelected(){
        String[] styles = taskContainer.getStyle().split(";");
        taskContainer.setStyle( styles[0] +";"+ styles[1] );
        Task task = (Task) taskContainer.getUserData();
        task.setSelect(false);
        taskContainer.setUserData(task);
    }

    void basic_view(){
        boolean pane_status = grayPane.isVisible();
        taskContainer.setPrefHeight(100);
        for (Node node : taskContainer.getChildren()) {
            node.setVisible(true);
        }
        nameTask.setVisible(true);
        nameTask.setDisable(false);
        nameTask_textField.setVisible(false);
        nameTask_textField.setDisable(true);
        //nameTask_textField.setVisible(false);
        grayPane.setVisible(false);
        AnchorPane.setTopAnchor(openSettings_button, 35.0);
        AnchorPane.setBottomAnchor(openSettings_button, 27.0);
        grayPane.setVisible(pane_status);
        setStars(task.getRating());
        arrange();
    }

    void hidden_view(){
        boolean pane_status = grayPane.isVisible();
        taskContainer.setPrefHeight(30);
        for (Node node : taskContainer.getChildren()) {
            node.setVisible(false);
        }
        nameTask.setVisible(true);
        nameTask.setDisable(false);
        nameTask_textField.setVisible(false);
        nameTask_textField.setDisable(true);
        //nameTask.setVisible(true);
        noteTask.setVisible(true);
        openSettings_button.setVisible(true);
        didTask_button.setVisible(true);
        completnessBar.setVisible(true);
        AnchorPane.setTopAnchor(openSettings_button, 5.0);
        AnchorPane.setBottomAnchor(openSettings_button, 5.0);
        grayPane.setVisible(pane_status);
        arrange();
    }

    void deselect(TextField textField) {
        Platform.runLater(() -> {
            if (!textField.getText().isEmpty() &&
                    textField.selectionProperty().get().getEnd() == 0) {
                deselect(textField);
            }else{
                textField.selectEnd();
                textField.deselect();
            }
        });
    }

    private static class MyException extends Exception {
        public MyException() {
            super ("Вы ввели слишком длинное название");
        }
    }
    void checkName(TextField bName) throws MyException,NullPointerException
    {
        String sName = bName.getText();
        if (sName.length() > 40) throw new MyException();
        if (sName.isEmpty()) throw new NullPointerException();
    }

    void enterTaskName(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Warning!");
        try {
            checkName(nameTask_textField);

            nameTask.setText(nameTask_textField.getText());

            nameTask_textField.setPrefWidth(106);
            nameTask.setText(nameTask_textField.getText());
            nameTask.setVisible(true);
            nameTask.setDisable(false);
            nameTask_textField.setVisible(false);
            nameTask_textField.setDisable(true);

            task.setName(nameTask_textField.getText());
        }
        catch(NullPointerException ex) {
            alert.setHeaderText("Пожалуйста, введите название. Поле не может быть пустым");
            alert.showAndWait();
        }
        catch(MyException ignored) {}
    }

    void setStars(int count){
        ObservableList<ImageView> list_img_stars = FXCollections.observableArrayList(
                star1,star2,star3,star4,star5);
        for(int i=0; i < list_img_stars.size() ; ++i){
            ImageView star = list_img_stars.get(i);
            if (i < count) {
                star.setVisible(true);
            }
            else {
                star.setVisible(false);
            }
        }
    }

    void addTaskData(){
        task = (Task) taskContainer.getUserData();
    }
    void back_to_front(){
        addTaskData();
        basic_view();
        nameTask.setText(task.getName());
        noteTask.setText(task.getNote()==null ? "" : task.getNote().getText());
        completnessBar.setProgress((double)task.getCompletness()/100);
        completnessSlider.setValue(task.getCompletness());
        setStars(task.getRating());
        if (task.isDone()){
            hidden_view();
            didTask_button.setSelected(true);
            grayPane.setVisible(true);
        }
        if (task.getDeadline() != null){
            dataField.setValue(task.getDeadline());
        }
        if (task.isNotice()){
            notice_img.setOpacity(1);
            notice_CheckBox.setSelected(true);
        }
        else{
            notice_img.setOpacity(0.2);
        }
    }

    @FXML
    void initialize()  {

        notice_CheckBox.setOnAction(actionEvent -> {
            if (notice_CheckBox.isSelected()){
                notice_img.setOpacity(1);
                task.setNotice(true);
            }
            else{
                notice_img.setOpacity(0.2);
            }
        });

        final StringConverter<LocalDate> defaultConverter = dataField.getConverter();
        dataField.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate value) {
                return defaultConverter.toString(value);
            }

            @Override
            public LocalDate fromString(String text) {
                try {
                    return defaultConverter.fromString(text);
                } catch (DateTimeParseException ex) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("Дата введена не корректно!");
                    alert.showAndWait();
                    dataField.getEditor().setText("");
                    return null;
                }
            }
        });
        dataField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                                Boolean oldPropertyValue, Boolean newPropertyValue){
                if (!newPropertyValue) {
                    task.setDeadline(dataField.getValue());
                }
            }
        });


        list_of_stars.setItems(list_for_stars);
        list_of_stars.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue,
                                Number number, Number number2) {
                String choice = list_of_stars.getItems().get((Integer) number2);
                setStars(choice.length());
                task.setRating(choice.length());
            }
        });

        openTask_button.setOnAction(actionEvent -> {
            int len = nameTask_textField.getText().length();
            int lenCAPS = nameTask_textField.getText().split("(?=\\p{Lu})").length;
            len = (int)((len - lenCAPS + lenCAPS*1.22)/10 * 5/4 * 1.2);
            int lines = Math.max(noteTask.getText().split("\r\n|\r|\n").length, len + 1);
            if (taskContainer.getPrefHeight() < lines*22){
                taskContainer.setPrefHeight(lines*22);
                openTask_button.getChildrenUnmodifiable().get(0).setRotate(-90);
            }
            else{
                taskContainer.setPrefHeight(100);
                openTask_button.getChildrenUnmodifiable().get(0).setRotate(90);
            }
            arrange();
        });

        openSettings_button.setOnAction(actionEvent -> {
            if (taskContainer.getPrefHeight() <= 100){
                basic_view();
                isSelected();
                taskContainer.setPrefHeight(200);
                openTask_button.getChildrenUnmodifiable().get(0).setRotate(-90);
            }
            else{
                taskContainer.setPrefHeight(100);
                openTask_button.getChildrenUnmodifiable().get(0).setRotate(90);
            }
            arrange();
        });


        taskContainer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Task task = (Task) taskContainer.getUserData();
                if (!task.isSelect()){
                    basic_view();
                    isSelected();
                }
                else{
                    hidden_view();
                    noSelected();
                }
            }
        });


        EventHandler<InputEvent> adaptive_extend = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {
                int len = nameTask_textField.getText().length();
                int lenCAPS = nameTask_textField.getText().split("(?=\\p{Lu})").length;
                len = (len - lenCAPS)*10 + lenCAPS*12 + 50;
                if (len >= 115){
                    nameTask_textField.setPrefWidth(len);
                }
                else{
                    nameTask_textField.setPrefWidth(115);
                }

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Warning!");
                try {
                    checkName(nameTask_textField);
                }
                catch(MyException myEx) {
                    nameTask_textField.setText(nameTask_textField.getText().substring(0, 40));
                    alert.setHeaderText(myEx.getMessage());
                    alert.showAndWait();
                }
                catch(NullPointerException ex){}
            }
        };

        nameTask.addEventHandler(MouseEvent.MOUSE_CLICKED,mouseEvent -> {
            basic_view();
            nameTask.setVisible(false);
            nameTask.setDisable(true);
            nameTask_textField.setVisible(true);
            nameTask_textField.setDisable(false);
            nameTask_textField.setText(nameTask.getText());
        });
        nameTask.setOnMouseClicked(adaptive_extend);

        nameTask_textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (newPropertyValue)
                {
                    deselect(nameTask_textField);
                    nameTask_textField.setUserData("FOCUS_ON");
                }
                else
                {
                    if (nameTask_textField.getUserData() != "PRESS_ENTER"){
                        enterTaskName();
                    }
                    nameTask_textField.setUserData("FOCUS_OFF");
                }
            }
        });
        nameTask_textField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                nameTask_textField.setUserData("PRESS_ENTER");
                enterTaskName();
            }
        });
        nameTask_textField.setOnKeyTyped(adaptive_extend);
        nameTask_textField.setOnMouseEntered(adaptive_extend);


        noteTask.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    task.getNote().setText(noteTask.getText());
                }
            }
        });


        didTask_button.onActionProperty().set(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (didTask_button.isSelected()) {
                    grayPane.setVisible(true);
                    hidden_view();
                    task.setDone(true);
                }
                else {
                    grayPane.setVisible(false);
                    basic_view();
                    task.setDone(false);
                }
            }
        });


        completnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                completnessBar.setProgress(completnessSlider.getValue()/100);
                task.setCompletness((int)completnessSlider.getValue());
            }
        });

    }

}


