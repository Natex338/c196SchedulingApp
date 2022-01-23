package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.c196schedulingapp.Database.AssessmentRepo;
import com.example.c196schedulingapp.Entity.Assessment;
import com.example.c196schedulingapp.Entity.Course;
import com.example.c196schedulingapp.R;
import com.example.c196schedulingapp.Util.DateParse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CreateAssessment extends AppCompatActivity {

    String assessmentName;
    String startDate;
    String endDate;
    EditText editName;
    EditText editSDate;
    EditText editEDate;
    RadioButton radioButtonOA;
    RadioButton radioButtonPA;
    int courseID;
    int assessmentID;
    int numAlert;
    String radioIDSelection;
    AssessmentRepo assessmentRepo;
    DatePickerDialog.OnDateSetListener date1;
    DatePickerDialog.OnDateSetListener date2;
    RadioGroup radioGroup;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        assessmentName = getIntent().getStringExtra("assessmentName");
        editName = findViewById(R.id.assessmentName);
        editName.setText(assessmentName);

        startDate = getIntent().getStringExtra("startDate");
        editSDate = findViewById(R.id.assessmentStart);
        editSDate.setText(startDate);

        endDate = getIntent().getStringExtra("endDate");
        editEDate = findViewById(R.id.assessmentEnd);
        editEDate.setText(endDate);

        assessmentRepo = new AssessmentRepo(getApplication());
        courseID = getIntent().getIntExtra("courseID", -1);
        radioGroup = findViewById(R.id.radioGroup);
       // RadioGroup rb1 = (RadioGroup)findViewById(R.id.radioGroup);

        radioIDSelection= getIntent().getStringExtra("assessmentType");


        radioButtonOA =(RadioButton)findViewById(R.id.radioButton1);
        radioButtonPA =(RadioButton)findViewById(R.id.radioButton2);

        if (radioIDSelection!=null) {
            if (radioIDSelection.equalsIgnoreCase("Performance Assessment")) {
                radioButtonPA.setChecked(true);
            } else if (radioIDSelection.equalsIgnoreCase("Objective Assessment")) {
                radioButtonOA.setChecked(true);
            } else
                radioButtonOA.setChecked(true);

        }


        if (courseID==-1) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) { courseID = extras.getInt("key2");
             }
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                // TODO fix to send correct data
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"This is Text to send"+ editName.getText());
                sendIntent.putExtra(Intent.EXTRA_TITLE,"This is a sent Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyStart:
                String dateFromString = editSDate.getText().toString();
                long trigger = DateParse.dateParse(dateFromString).getTime();
                Intent intent  = new Intent(CreateAssessment.this,MyReceiver.class);
                intent.putExtra("key","?");
                PendingIntent sender= PendingIntent.getBroadcast(CreateAssessment.this, ++numAlert, intent, 0);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.notifyEnd:
                String dateFromString2 = editEDate.getText().toString();
                long trigger2 = DateParse.dateParse(dateFromString2).getTime();
                Intent intent2  = new Intent(CreateAssessment.this,MyReceiver.class);
                intent2.putExtra("key","?");
                PendingIntent sender2= PendingIntent.getBroadcast(CreateAssessment.this, ++numAlert, intent2, 0);
                AlarmManager alarmManager2=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC_WAKEUP, trigger2, sender2);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
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
               assessmentID = assessmentRepo.getAllAssessments().size();
               Assessment newAssessment = new Assessment(++assessmentID, courseID,screenName,screenDate, screenDate2, radioIDSelection);
               assessmentRepo.insert(newAssessment);

           } else {
               Assessment oldAssessment = new Assessment(getIntent().getIntExtra("assessmentID", -1),courseID, screenName,screenDate, screenDate2,radioIDSelection);
               assessmentRepo.update(oldAssessment);
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

    public void checkButton(View view) {
        int radioID = radioGroup.getCheckedRadioButtonId();
        RadioButton oncSelected = findViewById(radioID);
        radioIDSelection = "" + oncSelected.getText();
        Toast.makeText(this, "Selected Radio Button: " + radioButtonOA.getText(),
                Toast.LENGTH_SHORT).show();

    }
}