package com.example.front.json;

import lombok.Data;

@Data
public class Token {
    public String token;
    @Override
    public String toString(){
        return token;
    }
}
