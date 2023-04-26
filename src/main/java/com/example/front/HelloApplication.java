package com.example.front;

import com.example.front.Controller.any.AnyLoginController;
import com.example.front.json.Token;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

public class HelloApplication extends Application {
    private static AnyLoginController anyLoginController;
    public static AnyLoginController getAnyLoginController() {
        return anyLoginController;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("any/any_login_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        anyLoginController = fxmlLoader.getController();
        stage.setTitle("login_page");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}