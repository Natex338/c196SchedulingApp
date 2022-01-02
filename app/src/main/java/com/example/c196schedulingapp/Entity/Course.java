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
   // private Date startDate;
   // private Date endDate;
    private String status;
    private int instructorID;

    public Course(int courseID, String courseTitle, int termID, String status, int instructorID) {
        this.termID = termID;
        this.courseID = courseID;
        this.courseTitle = courseTitle;
       // this.startDate = startDate;
        //this.endDate = endDate;
        this.status = status;
        this.instructorID=instructorID;

    }

    //Getters
    public int getTermID() {
        return termID;
    }
    public int getCourseID() {
        return courseID;
    }
    public String getCourseTitle() {
        return courseTitle;
    }
    /*public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }

     */
    public String getStatus() {
        return status;
    }
    public int getInstructorID() {
        return instructorID;
    }

    //setters
    public void setTermID(int termID) {
        this.termID = termID;
    }
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
/*    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

 */
    public void setStatus(String status) {
        this.status = status;
    }
    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }


    //override to string method
    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseTitle='" + courseTitle + '\'' +
                '}';
    }


}
