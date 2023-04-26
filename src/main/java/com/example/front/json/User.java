package com.example.front.json;

import javafx.beans.property.*;
import lombok.Data;

import java.util.List;
@Data
public class User {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String role;
    public int age;
    public List<Book> books;
    public boolean enabled;
    public String username;
    public List<Authority> authorities;
    public boolean accountNonExpired;
    public boolean credentialsNonExpired;
    public boolean accountNonLocked;

}
