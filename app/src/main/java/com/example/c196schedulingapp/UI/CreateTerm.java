package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AlarmManagerCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.c196schedulingapp.Database.DateConverter;
import com.example.c196schedulingapp.Database.TermRepo;
import com.example.c196schedulingapp.Entity.Term;
import com.example.c196schedulingapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CreateTerm extends AppCompatActivity {
    public int numAlert;
    String name;
    String startDate;
    EditText editName;
    EditText editDate;
    int id;
    TermRepo repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_term);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name= getIntent().getStringExtra("termName");
        editName=findViewById(R.id.termName);
        editName.setText(name);

        startDate = getIntent().getStringExtra("termStart");
        editDate = findViewById(R.id.termStart);
        editDate.setText(startDate);

        repository= new TermRepo(getApplication());

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"This is Text to send"+ editName.getText());
                sendIntent.putExtra(Intent.EXTRA_TITLE,"This is a sent Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
                return true;
            case R.id.notify:
                String dateFromString = editDate.getText().toString();
                long trigger = dateParse(dateFromString).getTime();
                Intent intent  = new Intent(CreateTerm.this,MyReceiver.class);
                intent.putExtra("key","?");
                PendingIntent sender= PendingIntent.getBroadcast(CreateTerm.this, ++numAlert, intent, 0);
                AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTerm(View view) {
    String screenName= editName.getText().toString();
    Date screenDate= dateParse(editDate.getText().toString());
        if (name==null) {
            id=repository.getAllTerms().get(repository.getAllTerms().size()-1).getTermID();
            Term newTerm = new Term(++id, screenName, DateConverter.Converters.dateToTimestamp(screenDate));
            repository.insert(newTerm);
        }
        else {
            Term oldTerm=new Term(getIntent().getIntExtra("termID", -1),screenName,DateConverter.Converters.dateToTimestamp(screenDate));
            repository.update(oldTerm);
        }

        Intent intent = new Intent (CreateTerm.this, MainActivity.class );
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public Date dateParse(String date){
        String dateFromString =date;
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date myDate = null;
        try{
            myDate= sdf.parse(dateFromString);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return myDate;
    }

    public void date(View view) {

    }
}

