package com.example.c196schedulingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.TypeConverter;

import com.example.c196schedulingapp.Entity.Term;
import com.example.c196schedulingapp.R;

import java.util.Date;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewHolder> {

    class ListViewHolder extends RecyclerView.ViewHolder {

        private final TextView listItemView;
        private final TextView listItemView1;

        private ListViewHolder(View itemView) {
            super(itemView);
            listItemView = itemView.findViewById(R.id.textView);
            listItemView1 = itemView.findViewById(R.id.textView1);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, CreateTerm.class);
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("termName", current.getTermName());
                    context.startActivity(intent);

                }
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflator;


    @NonNull
    @Override
    public ListViewAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflator.inflate(R.layout.list_items, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ListViewHolder holder, int position) {
        if(mTerms!=null){
            Term current=mTerms.get(position);
            int id=current.getTermID();
            holder.listItemView.setText((current.getTermName()));
            holder.listItemView1.setText(Integer.toString(current.getTermID()));
        }
        else{
            holder.listItemView.setText("No Thing Name");
            holder.listItemView1.setText("No Thing ID");
        }
    }

    public ListViewAdapter(Context context){
        mInflator=LayoutInflater.from(context);
        this.context=context;
    }


    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return mTerms.size();
    }
}
