package com.example.myhotels.Util;

import java.util.List;

public class Bookings {
    private String name;
    private List<Integer> id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public List<Integer> getId() {
        return id;
    }
}
