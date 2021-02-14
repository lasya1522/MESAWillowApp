package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.ViewHolder> {
    private List<DailyQuiz> dailyQuizs;
    private Context context;

    public OtherAdapter(List<DailyQuiz> dailyQuizs, Context context ){
        this.dailyQuizs = dailyQuizs;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_stressor, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyQuiz dq = dailyQuizs.get(position);
        holder.tv_stressor.setText(dq.getOther());

    }


    @Override
    public int getItemCount() {
        return dailyQuizs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_stressor;
        public Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            tv_stressor = itemView.findViewById(R.id.tv_stressor);
        }
    }
}
