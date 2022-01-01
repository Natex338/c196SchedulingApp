package com.example.c196schedulingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.c196schedulingapp.Database.Repository;
import com.example.c196schedulingapp.Entity.Term;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository repository= new Repository(getApplication());
        Term term = new Term(1,"Term1");
        repository.insert(term);
        Term term2 = new Term(2,"Term2");
        repository.insert(term2);
    }
}