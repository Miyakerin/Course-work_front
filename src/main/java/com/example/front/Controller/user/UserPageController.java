package com.example.front.Controller.user;

import com.example.front.HelloApplication;
import com.example.front.TableViewModels.Book;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserPageController {
    @FXML
    public Label firstName;
    @FXML
    public Label lastName;
    @FXML
    public Label email;
    @FXML
    public Label age;
    @FXML
    TableView loansView;
    @FXML
    TableView<Book> booksView;
    private String token;
    private User user;


    @FXML
    private void initialize() {
        token = HelloApplication.getAnyLoginController().getToken().toString();
        String url = "http://localhost:8080/api/user/me/info";
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .get().build();

            Call call = client.newCall(request);
            Response response = call.execute();
            user = new Gson().fromJson(response.body().string(), User.class);
            lastName.setText(user.getLastName());
            firstName.setText(user.getFirstName());
            age.setText(Integer.toString(user.getAge()));
            email.setText(user.getEmail());
        } catch (IOException e) {
            e.printStackTrace();
        }
        url = "http://localhost:8080/api/user/me/loans";
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .get().build();
            Call call = client.newCall(request);
            Response response = call.execute();
            List<com.example.front.json.Book> booksNotSimple = new Gson().fromJson(response.body().string(),
                    new TypeToken<List<com.example.front.json.Book>>(){}.getType());
            List<Book> books = new ArrayList<>();
            for (com.example.front.json.Book book:booksNotSimple) {
                books.add(new Book(book.id, book.name, book.description, book.genre,
                        book.author, book.ageRestriction, book.condition));
            }
            ObservableList<Book> observableListBooks = FXCollections.observableArrayList(books);

            TableColumn loanViewNameColumn = new TableColumn("name");
            TableColumn loanViewAuthorColumn = new TableColumn("author");
            TableColumn loanViewGenreColumn = new TableColumn("genre");
            TableColumn loanViewDescriptionColumn = new TableColumn("description");

            loansView.getColumns().addAll(loanViewNameColumn, loanViewAuthorColumn, loanViewDescriptionColumn, loanViewGenreColumn);

            loanViewNameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
            loanViewAuthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
            loanViewGenreColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
            loanViewDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("description"));

            loansView.setItems(observableListBooks);


            url = "http://localhost:8080/api/user/available/get";
            request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .get().build();
            call = client.newCall(request);
            response = call.execute();
            booksNotSimple = new Gson().fromJson(response.body().string(),
                    new TypeToken<List<com.example.front.json.Book>>(){}.getType());
            books = new ArrayList<>();
            for (com.example.front.json.Book book:booksNotSimple) {
                books.add(new Book(book.id, book.name, book.description, book.genre,
                        book.author, book.ageRestriction, book.condition));
            }
            observableListBooks = FXCollections.observableArrayList(books);

            TableColumn availableViewNameColumn = new TableColumn("name");
            TableColumn availableViewAuthorColumn = new TableColumn("author");
            TableColumn availableViewGenreColumn = new TableColumn("genre");
            TableColumn availableViewDescriptionColumn = new TableColumn("description");

            booksView.getColumns().addAll(availableViewNameColumn, availableViewAuthorColumn, availableViewDescriptionColumn, availableViewGenreColumn);

            availableViewNameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
            availableViewAuthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
            availableViewGenreColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));
            availableViewDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("description"));

            booksView.setItems(observableListBooks);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logoutButtonClickAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("any/any_login_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            Stage stage = new Stage();
            stage.setTitle("login");
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
