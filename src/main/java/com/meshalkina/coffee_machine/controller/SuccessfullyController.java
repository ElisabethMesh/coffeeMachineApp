package com.meshalkina.coffee_machine.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SuccessfullyController {
    @FXML
    private Button successfullyOK;

    @FXML
    void openNewStage(ActionEvent event) {
        Stage stage = (Stage) successfullyOK.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {

    }

}
