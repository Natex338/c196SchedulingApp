package com.example.c196schedulingapp.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessment_table",
        foreignKeys = {@ForeignKey(entity = Course.class,
                parentColumns = "courseID",
                childColumns = "courseID",
                onDelete = ForeignKey.CASCADE)})

public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseID;
    private String assessmentName;
    private Date startDate;
    private Date endDate;
   // private Enum ASSESSMENT_TYPE;


    public Assessment(int assessmentID, int courseID, String assessmentName, Date startDate, Date endDate){
        this.assessmentID =assessmentID;
        this.courseID=courseID;
        this.assessmentName=assessmentName;
        this.startDate=startDate;
        this.endDate=endDate;
        //this.ASSESSMENT_TYPE=assessmentType;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

   // public Enum getASSESSMENT_TYPE() {
    //    return ASSESSMENT_TYPE;
 //   }

   // public void setASSESSMENT_TYPE(Enum ASSESSMENT_TYPE) {
  //      this.ASSESSMENT_TYPE = ASSESSMENT_TYPE;
  //  }





}
