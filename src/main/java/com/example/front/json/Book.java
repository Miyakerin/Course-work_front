package com.example.front.json;

import lombok.Data;

@Data
public class Book {
    public long id;
    public String name;
    public String description;
    public String genre;
    public String author;
    public int ageRestriction;
    public String condition;
}
