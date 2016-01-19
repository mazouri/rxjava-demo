package com.mazouri.rxjava.model;

import java.util.List;

/**
 * Created by wangdong on 16-1-19.
 */
public class Student {
    String name;
    List<Course> courses;

    public Student(){}

    public Student(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
