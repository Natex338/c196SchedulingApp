package com.example.c196schedulingapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.Entity.Term;

import java.util.List;
@Dao
public interface TermDAO {

//TERMS CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("Select * FROM TERM_TABLE ORDER BY TERMID ASC")
    List<Term> getAllTerms();



//Courses CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("Select * FROM COURSE_TABLE ORDER BY CourseID ASC")
    List<Course> getAllCourses();




}
