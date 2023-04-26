package com.example.front.TableViewModels;

import javafx.beans.property.SimpleStringProperty;

public class Authority {
    public SimpleStringProperty authority;

    public Authority(String authority) {
        this.authority = new SimpleStringProperty(authority);
    }
}
