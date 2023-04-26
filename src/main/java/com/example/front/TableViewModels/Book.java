package com.example.front.TableViewModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

public class Book {
    public SimpleLongProperty id;
    public SimpleStringProperty name;
    public SimpleStringProperty description;
    public SimpleStringProperty genre;
    public SimpleStringProperty author;
    public SimpleIntegerProperty ageRestriction;
    public SimpleStringProperty condition;

    public Book(long id, String name, String description, String genre,
                String author, int ageRestriction, String condition) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.genre = new SimpleStringProperty(genre);
        this.author = new SimpleStringProperty(author);
        this.condition = new SimpleStringProperty(condition);
        this.ageRestriction = new SimpleIntegerProperty(ageRestriction);
    }

    public int getAgeRestriction() {
        return ageRestriction.get();
    }

    public long getId() {
        return id.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public String getCondition() {
        return condition.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getGenre() {
        return genre.get();
    }

    public String getName() {
        return name.get();
    }
}
