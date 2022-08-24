package com.meshalkina.coffee_machine.controller;

import com.meshalkina.coffee_machine.CoffeeMachineController;
import com.meshalkina.coffee_machine.service.BasketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class PayWindowController {
    @FXML
    private TextField sumForPay;

    @FXML
    private Text total;

    private BasketService basketService = new BasketService();

    @FXML
    void initialize() {
        total.setText(String.valueOf(basketService.getSum()));

    }

    public void openNewStage(ActionEvent event) throws IOException {
        Double sumFromField = 0.0;
        try {
            sumFromField = Double.parseDouble(sumForPay.getText());
        } catch (NumberFormatException e) {
            openWindowError();
            sumForPay.clear();
            return;
        }

        Double sumFromBasket = basketService.getSum();
        if (sumFromBasket.equals(sumFromField)) {
            basketService.pay();
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            openMainWindow();
            openWindowSuccess();
        } else {
            openWindowError();
            sumForPay.clear();
        }
    }

    private void openWindowError() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CoffeeMachineController.class.getResource("error.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 100);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Error");
        stage.show();
    }

    private void openWindowSuccess() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CoffeeMachineController.class.getResource("successfully.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 100);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Success");
        stage.show();
    }

    private void openMainWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CoffeeMachineController.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Coffee machine");
        stage.show();
    }

    public void cancelAction(ActionEvent event) throws IOException {
        openMainWindow();
        final Node source = (Node) event.getSource();
        final Stage stage1 = (Stage) source.getScene().getWindow();
        stage1.close();
    }
}
