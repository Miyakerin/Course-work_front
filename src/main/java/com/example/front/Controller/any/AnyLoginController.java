package com.example.front.Controller.any;

import com.example.front.HelloApplication;
import com.example.front.json.Token;
import com.example.front.json.User;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import okhttp3.*;

import java.io.IOException;
//@RequiredArgsConstructor
public class AnyLoginController {
    @FXML
    TextField email;
    @FXML
    TextField pass;
    private Token token;

    public Token getToken() {
        return token;
    }

    @FXML
    public void onRegisterButtonClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("any/any_register_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 600);
            Stage stage = new Stage();
            stage.setTitle("register");
            stage.setScene(scene);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onLoginButton(ActionEvent actionEvent){
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String url = "http://localhost:8080/api/auth/authenticate";
        String json = "{\"email\":\"" + email.getText() + "\"," +
                "\"password\":\"" + pass.getText() + "\"}";
        try {
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            String response_token = response.body().string();
            if (response.code() != 200) {
                throw new IOException();
            }
            token = new Gson().fromJson(response_token, Token.class);

            request = new Request.Builder()
                    .url("http://localhost:8080/api/user/me/info")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token.getToken())
                    .get().build();

            call = client.newCall(request);
            response = call.execute();
            User user = new Gson().fromJson(response.body().string(), User.class);
            if (user.role.equals("ADMIN")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin/admin_page.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 1200);
                Stage stage = new Stage();
                stage.setTitle("admin_page");
                stage.setScene(scene);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
            if (user.role.equals("USER")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user/user_page.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage stage = new Stage();
                stage.setTitle("user_page");
                stage.setScene(scene);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }
            if (user.role.equals("EMPLOYEE")) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("employee/employee_page.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 1200);
                Stage stage = new Stage();
                stage.setTitle("employee_page");
                stage.setScene(scene);
                stage.show();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}