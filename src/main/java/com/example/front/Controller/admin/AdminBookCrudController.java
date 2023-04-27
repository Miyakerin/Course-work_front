package com.example.front.Controller.admin;

import com.example.front.HelloApplication;
import com.example.front.TableViewModels.Book;
import com.example.front.json.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminBookCrudController {
    @FXML
    public TableView<Book> table;
    @FXML
    public TextField name_post;
    @FXML
    public TextField author_post;
    @FXML
    public TextField genre_post;
    @FXML
    public TextField description_post;
    @FXML
    public TextField condition_post;
    @FXML
    public TextField ageRestrict_post;
    @FXML
    public TextField deleteId;
    @FXML
    public TextField findId;
    @FXML
    public Label id_find;
    @FXML
    public Label name_find;
    @FXML
    public Label author_find;
    @FXML
    public Label genre_find;
    @FXML
    public Label description_find;
    @FXML
    public Label condition_find;
    @FXML
    public Label age_restrict_find;
    @FXML
    public TextField loanerId_post;

    private String token;
    private User user;

    @FXML
    private void initialize() {
        token = HelloApplication.getAnyLoginController().getToken().toString();
        String url = "http://localhost:8080/api/employee/books/get";
        OkHttpClient client = new OkHttpClient().newBuilder().build();
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
            TableColumn tableViewIdColumn = new TableColumn("id");
            TableColumn tableViewNameColumn = new TableColumn("name");
            TableColumn tableViewAuthorColumn = new TableColumn("author");
            TableColumn tableViewGenreColumn = new TableColumn("genre");
            TableColumn tableViewDescriptionColumn = new TableColumn("description");
            TableColumn tableViewConditionColumn =  new TableColumn("condition");
            TableColumn tableViewAgeRestrictColumn = new TableColumn("ageRestriction");


            table.getColumns().addAll(tableViewIdColumn, tableViewNameColumn, tableViewAuthorColumn,
                    tableViewGenreColumn, tableViewDescriptionColumn, tableViewConditionColumn, tableViewAgeRestrictColumn);

            tableViewIdColumn.setCellValueFactory(new PropertyValueFactory<Book, Long>("id"));
            tableViewNameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
            tableViewAuthorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
            tableViewGenreColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("genre"));

            tableViewDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("description"));
            tableViewConditionColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("condition"));
            tableViewAgeRestrictColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("ageRestriction"));

            table.setItems(observableListBooks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void findByIdAction(ActionEvent actionEvent) throws IOException {

        String url = "http://localhost:8080/api/employee/books/get/" + findId.getText();
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        com.example.front.json.Book book = new Gson().fromJson(response.body().string(), com.example.front.json.Book.class);

        id_find.setText(Long.toString(book.id));
        name_find.setText(book.name);
        author_find.setText(book.author);
        genre_find.setText(book.genre);
        description_find.setText(book.description);
        condition_find.setText(book.condition);
        age_restrict_find.setText(Integer.toString(book.ageRestriction));

    }

    public void postAction(ActionEvent actionEvent) {
        String url = "http://localhost:8080/api/employee/books/post";
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        try {
            String json =
                    "{" +
                    "\"loaner_id\": " + loanerId_post.getText() + "," +
                    "\"name\": " + "\"" + name_post.getText() + "\"," +
                    "\"author\": " + "\"" + author_post.getText() + "\"," +
                    "\"genre\": " + "\"" + genre_post.getText() + "\"," +
                    "\"description\": " + "\"" + description_post.getText() + "\"," +
                    "\"condition\": " + "\"" + condition_post.getText() + "\"," +
                    "\"ageRestriction\": " + ageRestrict_post.getText() +
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
                url = "http://localhost:8080/api/employee/books/get";
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer " + token)
                        .get().build();
                call = client.newCall(request);
                response = call.execute();

                List<com.example.front.json.Book> booksNotSimple = new Gson().fromJson(response.body().string(),
                        new TypeToken<List<com.example.front.json.Book>>(){}.getType());
                List<Book> books = new ArrayList<>();
                for (com.example.front.json.Book book:booksNotSimple) {
                    books.add(new Book(book.id, book.name, book.description, book.genre,
                            book.author, book.ageRestriction, book.condition));
                }
                ObservableList<Book> observableListBooks = FXCollections.observableArrayList(books);
                table.getItems().clear();
                table.setItems(observableListBooks);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAction(ActionEvent actionEvent) {
        String url = "http://localhost:8080/api/employee/books/delete/" + deleteId.getText();
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
                url = "http://localhost:8080/api/employee/books/get";
                request = new Request.Builder()
                        .url(url)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer " + token)
                        .get().build();
                call = client.newCall(request);
                response = call.execute();

                List<com.example.front.json.Book> booksNotSimple = new Gson().fromJson(response.body().string(),
                        new TypeToken<List<com.example.front.json.Book>>(){}.getType());
                List<Book> books = new ArrayList<>();
                for (com.example.front.json.Book book:booksNotSimple) {
                    books.add(new Book(book.id, book.name, book.description, book.genre,
                            book.author, book.ageRestriction, book.condition));
                }
                ObservableList<Book> observableListBooks = FXCollections.observableArrayList(books);
                table.getItems().clear();
                table.setItems(observableListBooks);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
