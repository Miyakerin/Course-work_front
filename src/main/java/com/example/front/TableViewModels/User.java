package com.example.front.TableViewModels;

import com.example.front.json.Authority;
import com.example.front.json.Book;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

import java.util.List;
public class User {
    public SimpleLongProperty id;
    public SimpleStringProperty firstName;
    public SimpleStringProperty lastName;
    public SimpleStringProperty email;
    public SimpleStringProperty password;
    public SimpleStringProperty role;
    public SimpleIntegerProperty age;
    public SimpleListProperty<Book> books;
    public SimpleBooleanProperty enabled;
    public SimpleStringProperty username;
    public SimpleListProperty<Authority> authorities;
    public SimpleBooleanProperty accountNonExpired;
    public SimpleBooleanProperty credentialsNonExpired;
    public SimpleBooleanProperty accountNonLocked;

    public User(long id, String firstName, String lastName, String email, String password, String role,
                int age, List<Book> books, boolean enabled, String username, List<Authority> authorities,
                boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked){
        this.id = new SimpleLongProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
        this.age = new SimpleIntegerProperty(age);
        ObservableList<Book> observableListBooks = FXCollections.observableArrayList(books);
        this.books = new SimpleListProperty<Book>(observableListBooks);
        this.enabled = new SimpleBooleanProperty(enabled);
        this.username = new SimpleStringProperty(username);
        ObservableList<Authority> observableListAuthority = FXCollections.observableArrayList(authorities);
        this.authorities = new SimpleListProperty<Authority>(observableListAuthority);
        this.accountNonExpired = new SimpleBooleanProperty(accountNonExpired);
        this.credentialsNonExpired = new SimpleBooleanProperty(credentialsNonExpired);
        this.accountNonLocked = new SimpleBooleanProperty(accountNonExpired);
    }

    public User(long id, String firstName, String lastName, String email, String password, String role,
                int age) {
        this.id = new SimpleLongProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
        this.age = new SimpleIntegerProperty(age);
    }

    public long getId() {
        return id.get();
    }

    public String getEmail() {
        return email.get();
    }

    public int getAge() {
        return age.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getRole() {
        return role.get();
    }

    public ObservableList<Authority> getAuthorities() {
        return authorities.get();
    }

    public ObservableList<Book> getBooks() {
        return books.get();
    }

    public String getUsername() {
        return username.get();
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired.get();
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked.get();
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired.get();
    }
}
