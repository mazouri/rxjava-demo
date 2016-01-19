package com.mazouri.rxjava.model;

/**
 * Created by wangdong on 16-1-19.
 */
public class Course {
    String courseName;

    public Course(){}

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
