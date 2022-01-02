package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.c196schedulingapp.Database.CourseRepo;
import com.example.c196schedulingapp.Database.TermRepo;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.Entity.Term;
import com.example.c196schedulingapp.R;

public class MainActivity extends AppCompatActivity {

    private TermRepo termRepo;
    private CourseRepo courseRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TermRepo termRepo = new TermRepo(getApplication());
        CourseRepo courseRepo= new CourseRepo(getApplication());



        Term term = new Term(1,"Term1");
        termRepo.insert(term);
        Term term2 = new Term(2,"Term2");
        termRepo.insert(term2);
        Course course = new Course(1,"Test Course",term.getTermID(),"Active","Nathan","888888888","Email");
        courseRepo.insert(course);

    }




}