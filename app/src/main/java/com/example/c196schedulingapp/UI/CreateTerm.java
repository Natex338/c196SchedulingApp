package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196schedulingapp.Database.CourseRepo;
import com.example.c196schedulingapp.Database.TermRepo;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.Entity.Term;
import com.example.c196schedulingapp.R;
import com.example.c196schedulingapp.Util.DateParse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CreateTerm extends AppCompatActivity {
    public static int numAlert;
    String name;
    String startDate;
    String endDate;
    EditText editName;
    EditText editSDate;
    EditText editEDate;
    int termId;
    TermRepo repository;
    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    final Calendar myCalendar = Calendar.getInstance();
    CourseRepo courseRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_term);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = getIntent().getStringExtra("termName");
        editName = findViewById(R.id.termName);
        editName.setText(name);

        startDate = getIntent().getStringExtra("termStart");
        editSDate = findViewById(R.id.termStart);
        editSDate.setText(startDate);

        endDate = getIntent().getStringExtra("termEnd");
        editEDate = findViewById(R.id.termEnd);
        editEDate.setText(endDate);

        termId = getIntent().getIntExtra("termID", -1);
        repository = new TermRepo(getApplication());
        courseRepo = new CourseRepo(getApplication());

        if (name==null){
            FloatingActionButton button = findViewById(R.id.floatingActionButton);
            button.hide();
        }


        RecyclerView recyclerView = findViewById(R.id.recyclerCourseView);
        List<Course> allCourses = new ArrayList<>();
        for (Course course : courseRepo.getAllCourses()) {
            if (course.getTermID() == termId)
                allCourses.add(course);
        }

        final CourseViewAdapter courseAdapter = new CourseViewAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourse(allCourses);

        date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
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
                new DatePickerDialog(CreateTerm.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateTerm.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                // TODO fix to send correct data
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is Text to send" + editName.getText());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "This is a sent Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyStart:
                String dateFromString = editSDate.getText().toString();
                long trigger = DateParse.dateParse(dateFromString).getTime();
                Intent intentTStart = new Intent(CreateTerm.this, MyReceiver.class);
                intentTStart.putExtra("key", "Alert! Term: "+ name+ " starts: " + DateParse.dateParse(editSDate.getText().toString()));
                PendingIntent senderTStart = PendingIntent.getBroadcast(CreateTerm.this, ++numAlert, intentTStart, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, senderTStart);
                return true;
            case R.id.notifyEnd:
                String dateFromString2 = editEDate.getText().toString();
                long trigger2 = DateParse.dateParse(dateFromString2).getTime();
                Intent intentTEnd = new Intent(CreateTerm.this, MyReceiver.class);
                intentTEnd.putExtra("key", "Alert! Term: "+ name+ " Ends: " + DateParse.dateParse(editEDate.getText().toString()));
                PendingIntent senderTEnd = PendingIntent.getBroadcast(CreateTerm.this, ++numAlert, intentTEnd, 0);
                AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, senderTEnd);
                return true;
            case R.id.refresh:
                RecyclerView recyclerView = findViewById(R.id.recyclerCourseView);
                List<Course> allCourse = new ArrayList<>();
                for (Course course : courseRepo.getAllCourses()) {
                    if (course.getTermID() == termId)
                        allCourse.add(course);
                }
                final CourseViewAdapter courseAdapter = new CourseViewAdapter(this);
                recyclerView.setAdapter(courseAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                courseAdapter.setCourse(allCourse);
                return true;
            case R.id.delete:
                boolean termWithoutCourses= true;
                for (Course course : courseRepo.getAllCourses()) {
                    if (course.getTermID() == termId) {
                        termWithoutCourses = false;
                    }
                }
                if (termWithoutCourses) {
                    for (Term term : repository.getAllTerms()) {
                        if (term.getTermID() == termId) {
                            repository.delete(term);
                            Toast.makeText(this, "Term Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent3 = new Intent(getApplicationContext(), TermList.class);
                            startActivity(intent3);
                        }
                    }
                }
                else {
                    Toast.makeText(this, "Term Has Course, Delete Courses first!", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTerm(View view) {

        if (editEDate.getText().toString().trim().isEmpty() || editSDate.getText().toString().trim().isEmpty()|| editName.getText().toString().trim().isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "Please enter all required text fields before saving!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            String screenName = editName.getText().toString();
            Date screenDate = DateParse.dateParse(editSDate.getText().toString());
            Date screenDate2 = DateParse.dateParse(editEDate.getText().toString());

            if (name == null) {
                termId = repository.getAllTerms().size();
                Term newTerm = new Term(
                        ++termId,
                        screenName,
                        screenDate,
                        screenDate2);
                repository.insert(newTerm);
            } else {
                Term oldTerm = new Term(getIntent().getIntExtra("termID", -1), screenName, screenDate, screenDate2);
                repository.update(oldTerm);
            }
            Intent intent = new Intent(CreateTerm.this, TermList.class);
            startActivity(intent);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
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

    public void addCourse(View view) {
        Intent intent = new Intent(CreateTerm.this, CreateCourse.class);
        intent.putExtra("key", termId);
        startActivity(intent);
    }
}


