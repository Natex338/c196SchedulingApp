package com.example.c196schedulingapp.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.EnumMap;

@Entity(tableName = "course_table",
        foreignKeys = {@ForeignKey(entity = Term.class,
                parentColumns = "termID",
                childColumns = "termID",
                onDelete = ForeignKey.CASCADE)})


public class Course {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private int courseID;
    private String courseTitle;
    private Date startDate;
    private Date endDate;
    private String status;
    private int instructorID;




    public Course(int courseID, String courseTitle, int termID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.termID = termID;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseTitle='" + courseTitle + '\'' +
                '}';
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }
}
