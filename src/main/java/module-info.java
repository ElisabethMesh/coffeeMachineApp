module com.meshalkina.coffee_machine {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.meshalkina.coffee_machine to javafx.fxml;
    opens com.meshalkina.coffee_machine.model to javafx.fxml;

    exports com.meshalkina.coffee_machine;
    exports com.meshalkina.coffee_machine.model;
    exports com.meshalkina.coffee_machine.controller;
    opens com.meshalkina.coffee_machine.controller to javafx.fxml;
}