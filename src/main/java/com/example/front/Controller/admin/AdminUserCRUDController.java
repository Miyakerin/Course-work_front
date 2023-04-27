package com.example.front.Controller.admin;

import com.example.front.HelloApplication;
import com.example.front.json.Book;
import com.example.front.json.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminUserCRUDController {
    public Label find_id;
    @FXML
    public Label find_fName;
    @FXML
    public Label find_Pass;
    @FXML
    public Label find_Email;
    @FXML
    public Label find_Lname;
    @FXML
    public Label find_Role;
    @FXML
    public Label find_Age;
    @FXML
    public PasswordField postPass;
    @FXML
    public TextField postFName;
    @FXML
    public TextField postLName;
    @FXML
    public TextField postEmail;
    @FXML
    public TextField postAge;
    @FXML
    public TextField postRole;
    @FXML
    public TextField findId;
    @FXML
    public TextField deleteId;
    @FXML
    public TableView table;
    private String token;

    @FXML
    private void initialize() {
        token = HelloApplication.getAnyLoginController().getToken().toString();
        String url = "http://localhost:8080/api/admin/users/get";
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .get().build();

            Call call = client.newCall(request);
            Response response = call.execute();
            List<User> usersNotSimple = new Gson().fromJson(response.body().string(),
                    new TypeToken<List<User>>(){}.getType());
            List<com.example.front.TableViewModels.User> users = new ArrayList<>();
            for (User user:usersNotSimple) {
                users.add(new com.example.front.TableViewModels.User(user.id, user.firstName, user.lastName, user.email, user.password, user.role, user.age));
            }
            ObservableList<com.example.front.TableViewModels.User> observableListUsers = FXCollections.observableArrayList(users);
            TableColumn tableViewIdColumn = new TableColumn("id");
            TableColumn tableViewFirstNameColumn = new TableColumn("First name");
            TableColumn tableViewLastNameColumn = new TableColumn("Last Name");
            TableColumn tableViewEmailColumn = new TableColumn("email");
            TableColumn tableViewPasswordColumn = new TableColumn("password");
            TableColumn tableViewRoleColumn =  new TableColumn("role");
            TableColumn tableViewAgeColumn = new TableColumn("age");


            table.getColumns().addAll(tableViewIdColumn, tableViewFirstNameColumn, tableViewLastNameColumn,
                    tableViewEmailColumn, tableViewPasswordColumn, tableViewRoleColumn, tableViewAgeColumn);

            tableViewIdColumn.setCellValueFactory(new PropertyValueFactory<com.example.front.TableViewModels.User, Long>("id"));
            tableViewFirstNameColumn.setCellValueFactory(new PropertyValueFactory<com.example.front.TableViewModels.User, String>("firstName"));
            tableViewLastNameColumn.setCellValueFactory(new PropertyValueFactory<com.example.front.TableViewModels.User, String>("lastName"));
            tableViewEmailColumn.setCellValueFactory(new PropertyValueFactory<com.example.front.TableViewModels.User, String>("email"));

            tableViewPasswordColumn.setCellValueFactory(new PropertyValueFactory<com.example.front.TableViewModels.User, String>("password"));
            tableViewRoleColumn.setCellValueFactory(new PropertyValueFactory<com.example.front.TableViewModels.User, String>("role"));
            tableViewAgeColumn.setCellValueFactory(new PropertyValueFactory<com.example.front.TableViewModels.User, Integer>("age"));

            table.setItems(observableListUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void backButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin/admin_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("admin page");
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteButtonAction(ActionEvent actionEvent) {
        String url = "http://localhost:8080/api/admin/users/delete/" + deleteId.getText();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .delete()
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.code() == 200) {
                url = "http://localhost:8080/api/admin/users/get";
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer " + token)
                        .get().build();

                call = client.newCall(request);
                response = call.execute();
                List<User> usersNotSimple = new Gson().fromJson(response.body().string(),
                        new TypeToken<List<User>>(){}.getType());
                List<com.example.front.TableViewModels.User> users = new ArrayList<>();
                for (User user:usersNotSimple) {
                    users.add(new com.example.front.TableViewModels.User(user.id, user.firstName, user.lastName, user.email, user.password, user.role, user.age));
                }
                ObservableList<com.example.front.TableViewModels.User> observableListUsers = FXCollections.observableArrayList(users);

                table.getItems().clear();
                table.setItems(observableListUsers);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findButtonAction(ActionEvent actionEvent) {
        String url = "http://localhost:8080/api/admin/users/get/" + findId.getText();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .get().build();
            Call call = client.newCall(request);
            Response response = call.execute();
            User user = new Gson().fromJson(response.body().string(), User.class);
            find_id.setText(Long.toString(user.id));
            find_fName.setText(user.firstName);
            find_Lname.setText(user.lastName);
            find_Email.setText(user.email);
            find_Pass.setText(user.password);
            find_Role.setText(user.role);
            find_Age.setText(Integer.toString(user.age));
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void postButtonAction(ActionEvent actionEvent) {
        String url = "http://localhost:8080/api/admin/users/post";
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        try {
            String json =
                    "{" +
                            "\"firstName\": " + "\"" + postFName.getText() + "\"," +
                            "\"lastName\": " + "\"" + postLName.getText() + "\"," +
                            "\"email\": " + "\"" + postEmail.getText() + "\"," +
                            "\"password\": " + "\"" + postPass.getText() + "\"," +
                            "\"age\": " + postAge.getText() + "," +
                            "\"role\": " + "\"" + postRole.getText() + "\"" +
                            "}";
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.code() == 200) {
                url = "http://localhost:8080/api/admin/users/get";
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer " + token)
                        .get().build();

                call = client.newCall(request);
                response = call.execute();
                List<User> usersNotSimple = new Gson().fromJson(response.body().string(),
                        new TypeToken<List<User>>(){}.getType());
                List<com.example.front.TableViewModels.User> users = new ArrayList<>();
                for (User user:usersNotSimple) {
                    users.add(new com.example.front.TableViewModels.User(user.id, user.firstName, user.lastName, user.email, user.password, user.role, user.age));
                }
                ObservableList<com.example.front.TableViewModels.User> observableListUsers = FXCollections.observableArrayList(users);

                table.getItems().clear();
                table.setItems(observableListUsers);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
