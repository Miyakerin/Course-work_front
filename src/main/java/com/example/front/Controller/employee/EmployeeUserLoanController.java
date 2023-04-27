package com.example.front.Controller.employee;

import com.example.front.HelloApplication;
import com.example.front.TableViewModels.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeUserLoanController {
    @FXML
    public TableView table;
    @FXML
    public TextField loanerId;
    private String token;
    @FXML
    private void initialize() {
        token = HelloApplication.getAnyLoginController().getToken().toString();
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
    }

    public void findLoanButtonAction(ActionEvent actionEvent) {
        String url = "http://localhost:8080/api/employee/books/getloan/" + loanerId.getText();
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

            table.getItems().clear();
            table.setItems(observableListBooks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBackButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("employee/employee_page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("employee page");
            stage.setScene(scene);
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
