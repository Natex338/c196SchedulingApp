package com.example.c196schedulingapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.c196schedulingapp.Database.TermRepo;
import com.example.c196schedulingapp.Entity.Term;
import com.example.c196schedulingapp.R;

import java.util.Objects;

public class CreateTerm extends AppCompatActivity {

    String name;
    EditText editName;
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
        repository= new TermRepo(getApplication());

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTerm(View view) {
    String screenName= editName.getText().toString();
        if (name==null) {
            id=repository.getAllTerms().get(repository.getAllTerms().size()-1).getTermID();
            Term newTerm = new Term(++id, screenName);
            repository.insert(newTerm);
        }
        else {
            Term oldTerm=new Term(getIntent().getIntExtra("termID", -1),screenName);
            repository.update(oldTerm);
        }

        Intent intent = new Intent (CreateTerm.this, MainActivity.class );
        startActivity(intent);
    }
}

