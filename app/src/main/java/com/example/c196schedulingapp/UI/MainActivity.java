package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196schedulingapp.Database.AssessmentRepo;
import com.example.c196schedulingapp.Database.CourseRepo;
import com.example.c196schedulingapp.Database.TermRepo;
import com.example.c196schedulingapp.Entity.Assessment;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.Entity.Term;
import com.example.c196schedulingapp.R;
import com.example.c196schedulingapp.Util.ASSESSMENT_TYPE;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TermRepo termRepo;
    private CourseRepo courseRepo;
    private AssessmentRepo assessmentRepo;
    int testCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        termRepo = new TermRepo(getApplication());
        courseRepo = new CourseRepo(getApplication());
        assessmentRepo = new AssessmentRepo(getApplication());

        setSampleDatabase();

        List<Term> allTerms=termRepo.getAllTerms();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);

        final TermViewAdapter termAdapter = new TermViewAdapter(this);
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
                final TermViewAdapter termAdapter = new TermViewAdapter(this);
                termAdapter.setTerms(allTerms);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(allTerms);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_recyclerview,menu);
        return true;
    }


    public void setSampleDatabase(){
        ++testCount;
        Term term = new Term(1, "Term1", java.util.Calendar.getInstance().getTime(),new  Date("11/01/2022"));
        termRepo.insert(term);
        Term term2 = new Term(2, "Term2",java.util.Calendar.getInstance().getTime(),new  Date("11/01/2022"));
        termRepo.insert(term2);
        Course course = new Course(1, "Test Course", term.getTermID(), "Active", "Nathan", "888888888", "Email",new  Date("11/01/2022"),new  Date("11/01/2022"));
        courseRepo.insert(course);

        Assessment assessment= new Assessment(1,course.getCourseID(),"Test Assessment",new  Date("11/01/2022"),new  Date("11/01/2022"));
        assessmentRepo.insert(assessment);
    }

}