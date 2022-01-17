package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196schedulingapp.Database.AssessmentRepo;
import com.example.c196schedulingapp.Database.CourseRepo;
import com.example.c196schedulingapp.Database.TermRepo;
import com.example.c196schedulingapp.Entity.Assessment;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.R;
import com.example.c196schedulingapp.Util.DateParse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateAssessment extends AppCompatActivity {

    String assessmentName;
    String startDate;
    String endDate;
    EditText editName;
    EditText editSDate;
    EditText editEDate;
    int id;
    int courseID;
    int assessmentID=1;
    AssessmentRepo repository;
    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment);

        assessmentName = getIntent().getStringExtra("assessmentName");
        editName = findViewById(R.id.assessmentName);
        editName.setText(assessmentName);

        startDate = getIntent().getStringExtra("startDate");
        editSDate = findViewById(R.id.assessmentStart);
        editSDate.setText(startDate);

        endDate = getIntent().getStringExtra("endDate");
        editEDate = findViewById(R.id.assessmentEnd);
        editEDate.setText(endDate);

        repository = new AssessmentRepo(getApplication());
        courseID = getIntent().getIntExtra("courseID", -1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("key");
        }


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
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editSDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateAssessment.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateAssessment.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void onCancel(View view) {
        this.finish();
    }

    public void saveAssessment(View view) {


       if (editEDate.getText().toString().trim().isEmpty() || editSDate.getText().toString().trim().isEmpty()|| editName.getText().toString().trim().isEmpty()) {
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

           if (assessmentName == null) {
               assessmentID = repository.getAllAssessments().get(repository.getAllAssessments().size()-1).getAssessmentID();
               Assessment newAssessment = new Assessment(++assessmentID,id,assessmentName,screenDate,screenDate2);
               repository.insert(newAssessment);
           } else {
               //Assessment oldAssessment = new Assessment(getIntent().getIntExtra("AssessmentID", -1), screenName, courseID,);
               //repository.update(oldAssessment);
           }
       }
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