package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196schedulingapp.Database.AssessmentRepo;
import com.example.c196schedulingapp.Database.CourseRepo;
import com.example.c196schedulingapp.Entity.Assessment;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.Entity.Term;
import com.example.c196schedulingapp.R;
import com.example.c196schedulingapp.Util.DateParse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CreateCourse extends AppCompatActivity {
    String name;
    String startDate;
    String endDate;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String status = "Working On it";
    EditText editName;
    EditText editSDate;
    EditText editEDate;
    EditText editInstructor;
    EditText editInstructorEmail;
    EditText editInstructorPhone;
    int courseID;
    int termID;
    CourseRepo courseRepo;
    AssessmentRepo assessmentRepo;

    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        name = getIntent().getStringExtra("courseTitle");
        editName = findViewById(R.id.courseName);
        editName.setText(name);

        startDate = getIntent().getStringExtra("courseStart");
        editSDate = findViewById(R.id.courseStart);
        editSDate.setText(startDate);

        endDate = getIntent().getStringExtra("courseEnd");
        editEDate = findViewById(R.id.courseEnd);
        editEDate.setText(endDate);

        instructorName = getIntent().getStringExtra("instructorName");
        editInstructor = findViewById(R.id.instructorName);
        editInstructor.setText(instructorName);

        instructorEmail = getIntent().getStringExtra("instructorEmail");
        editInstructorEmail = findViewById(R.id.instructorEmail);
        editInstructorEmail.setText(instructorEmail);

        instructorPhone = getIntent().getStringExtra("instructorPhone");
        editInstructorPhone = findViewById(R.id.instructorPhone);
        editInstructorPhone.setText(instructorPhone);

        termID = getIntent().getIntExtra("termID", -1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            termID = extras.getInt("key");
        }
        courseRepo = new CourseRepo(getApplication());
        assessmentRepo= new AssessmentRepo(getApplication());

        RecyclerView recyclerView = findViewById(R.id.recyclerAssessmentView);
        List<Assessment> allAssessment = new ArrayList<>();
        allAssessment.addAll(assessmentRepo.getAllAssessments());
        /*
        for (Assessment assessment : assessmentRepo.getAllAssessments()) {
            if (assessment.getCourseID() == courseID)
                allAssessment.add(assessment);
        }

         */
        final AssessmentViewAdapter assessmentAdapter = new AssessmentViewAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessment);




        date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateCourse.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateCourse.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }


    public void addCAssessment(View view) {
        Intent intent = new Intent(CreateCourse.this, CreateAssessment.class);
        intent.putExtra("key",courseID);
        startActivity(intent);

    }

    public void saveCourse(View view) {
        if (editEDate.getText().toString().trim().isEmpty() || editSDate.getText().toString().trim().isEmpty()|| editName.getText().toString().trim().isEmpty()|| editInstructor.getText().toString().trim().isEmpty()
                || editInstructorEmail.getText().toString().trim().isEmpty()|| editInstructorPhone.getText().toString().trim().isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "Please enter all required text fields before saving!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            String screenName = editName.getText().toString();
            Date screenDate = DateParse.dateParse(editSDate.getText().toString());
            Date screenDate2 = DateParse.dateParse(editEDate.getText().toString());
            String screenInstructor = editInstructor.getText().toString();
            String screenInstructorEmail = editInstructorEmail.getText().toString();
            String screenInstructorPhone = editInstructorPhone.getText().toString();

            if (name == null) {
                courseID = courseRepo.getAllCourses().get(courseRepo.getAllCourses().size() - 1).getCourseID();
                Course newCourse = new Course(++courseID, screenName, termID, status, screenInstructor, screenInstructorPhone, screenInstructorEmail, screenDate, screenDate2);
                courseRepo.insert(newCourse);
            } else {
                Course oldCourse = new Course(getIntent().getIntExtra("CourseID", -1), screenName, termID, status, screenInstructor, screenInstructorPhone, screenInstructorEmail, screenDate, screenDate2);
                courseRepo.update(oldCourse);
            }
        }
        this.finish();
    }


    public void onCancel(View view) {
        this.finish();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editSDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEDate.setText(sdf.format(myCalendar.getTime()));
    }
}