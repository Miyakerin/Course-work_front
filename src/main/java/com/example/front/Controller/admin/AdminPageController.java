package com.example.front.Controller.admin;

import com.example.front.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminPageController {
    @FXML
    private Label myToken;
    @FXML
    protected void onShowTokenButton() {
        myToken.setText(HelloApplication.getAnyLoginController().getToken().toString());
    }
}
