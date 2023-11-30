package org.leti.oop.kursach;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static org.leti.oop.kursach.Data.setCurrentUser;

public class AuthenticationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button enter;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    boolean found = false;

    private Main main;

    void setMain(Main main){
        this.main = main;
    }

    @FXML
    void initialize() {

        enter.setOnAction(actionEvent -> {
            List<User> allUsers = Data.getInstance().getUsers();
            for (User user : allUsers){
                if ( user.getLogin().equals(login.getText()) &&
                        user.getPassword().equals(password.getText()) ) {
                    found = true;
                    setCurrentUser(user);
                    main.stageBaseWindow();
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Сообщение");
            if (!found){
                alert.setHeaderText("Пользователь с таким логином и паролем не найден");
            }
            else{
                alert.setHeaderText("Добро пожаловать!");
            }
            alert.showAndWait();
        });

    }

}
