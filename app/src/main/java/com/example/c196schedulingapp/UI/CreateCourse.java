package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.c196schedulingapp.R;

public class CreateCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
    }

    public void addCAssessment(View view) {

    }

    public void saveCourse(View view) {
    }

    public void onCancel(View view) {
        this.finish();
    }
}