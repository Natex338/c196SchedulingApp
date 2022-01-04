package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196schedulingapp.Database.CourseRepo;
import com.example.c196schedulingapp.Database.TermRepo;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.Entity.Term;
import com.example.c196schedulingapp.R;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TermRepo termRepo;
    private CourseRepo courseRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        termRepo = new TermRepo(getApplication());
        courseRepo = new CourseRepo(getApplication());

        setSampleDatabase();

        List<Term> allTerms=termRepo.getAllTerms();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);

        final ListViewAdapter termAdapter = new ListViewAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);


    }

    public void floatingActionButton(View view) {
        Intent intent = new Intent(MainActivity.this, CreateTerm.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refresh:
                List<Term> allTerms=termRepo.getAllTerms();
                RecyclerView recyclerView=findViewById(R.id.recyclerView);
                final ListViewAdapter termAdapter = new ListViewAdapter(this);
                termAdapter.setTerms(allTerms);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(allTerms);
        }
        return super.onOptionsItemSelected(item);
    }


    public void setSampleDatabase(){
        Term term = new Term(1, "Term1");
        termRepo.insert(term);
        Term term2 = new Term(2, "Term2");
        termRepo.insert(term2);
        Course course = new Course(1, "Test Course", term.getTermID(), "Active", "Nathan", "888888888", "Email");
        courseRepo.insert(course);
    }

}