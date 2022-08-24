package com.meshalkina.coffee_machine.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ErrorController {
    @FXML
    private Button errorOK;

    @FXML
    void openNewStage(ActionEvent event) {
        Stage stage = (Stage) errorOK.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

    }

}

