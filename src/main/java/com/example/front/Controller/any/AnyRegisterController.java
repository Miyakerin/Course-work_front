package com.example.front.Controller.any;

import com.example.front.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.*;

public class AnyRegisterController {
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField email;
    @FXML
    public TextField password;
    @FXML
    public TextField age;

    public void registerUser(ActionEvent actionEvent) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            String url = "http://localhost:8080/api/auth/register";
            String json = "{" +
                    "\"firstName\": " + "\"" + firstName.getText() + "\"," +
                    "\"lastName\": " + "\"" + lastName.getText() + "\"," +
                    "\"email\": " + "\"" + email.getText() + "\"," +
                    "\"password\": " + "\"" + password.getText() + "\"," +
                    "\"age\": " + age.getText() +
                            "}";
            System.out.println(json);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("any/any_login_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 600);
            Stage stage = new Stage();
            stage.setTitle("login");
            stage.setScene(scene);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
