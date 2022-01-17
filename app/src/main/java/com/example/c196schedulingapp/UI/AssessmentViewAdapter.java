package com.example.c196schedulingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196schedulingapp.Entity.Assessment;
import com.example.c196schedulingapp.R;
import com.example.c196schedulingapp.Util.DateParse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AssessmentViewAdapter extends RecyclerView.Adapter<AssessmentViewAdapter.AssessmentViewHolder> {

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflator;


    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView listItemView;
        private final TextView listItemView1;
        private final TextView listItemView2;


        private AssessmentViewHolder(View itemView) {
            super(itemView);
            listItemView = itemView.findViewById(R.id.textView);
            listItemView1 = itemView.findViewById(R.id.textView1);
            listItemView2 = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, Assessment.class);
                    intent.putExtra("courseTitle", current.getAssessmentName());
                    intent.putExtra("courseStart", DateParse.dateParseString(current.getStartDate()));
                    intent.putExtra("courseEnd", DateParse.dateParseString(current.getEndDate()));
                    intent.putExtra("termID", current.getAssessmentID());
                    context.startActivity(intent);

                }
            });
        }
    }

    @NonNull
    @Override
    public AssessmentViewAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.list_items, parent, false);
        return new AssessmentViewAdapter.AssessmentViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {

            Assessment current = mAssessments.get(position);
            int id = current.getAssessmentID();
            holder.listItemView.setText((current.getAssessmentName()));
            holder.listItemView1.setText(DateParse.dateParseString(current.getStartDate()));
            holder.listItemView2.setText(DateParse.dateParseString(current.getEndDate()));
        } else {
            holder.listItemView.setText("No Thing Name");
            holder.listItemView1.setText("No Thing ID");
            holder.listItemView2.setText("No Thing ID");
        }
    }

    public AssessmentViewAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }


    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    public String dateFormat(String date) throws ParseException {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(date);
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}

