package com.example.sqlitedemo;

public class Student {
    private int id;
    private String name;
    private String location;
    private String course;

    public Student() {
    }

    public Student(int id, String name, String location, String course) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.course = course;
    }

    public Student(String name, String location, String course) {
        this.name = name;
        this.location = location;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
