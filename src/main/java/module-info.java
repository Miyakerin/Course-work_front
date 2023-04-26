module com.example.front {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires static lombok;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires okhttp3;
    requires com.google.gson;
    requires okio;
    requires annotations;

    opens com.example.front to javafx.fxml;
    exports com.example.front;

    opens com.example.front.Controller.user to javafx.fxml;
    exports com.example.front.Controller.user;

    opens com.example.front.Controller.any to javafx.fxml;
    exports com.example.front.Controller.any;

    opens com.example.front.Controller.admin to javafx.fxml;
    exports com.example.front.Controller.admin;

    opens com.example.front.Controller.employee to javafx.fxml;
    exports com.example.front.Controller.employee;

    opens com.example.front.json to com.google.gson;
    exports com.example.front.json;

    opens com.example.front.TableViewModels to java.base;
    exports com.example.front.TableViewModels;
}