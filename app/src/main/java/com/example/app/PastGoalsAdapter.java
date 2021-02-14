package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PastGoalsAdapter extends RecyclerView.Adapter<PastGoalsAdapter.ViewHolder> {
    private List<Goal> goals;
    private Context context;

    public PastGoalsAdapter(List<Goal> goals, Context context ){
        this.goals = goals;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_past_goal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Goal goal = goals.get(position);

       holder.tv_text.setText(goal.getGoalText());
       holder.tv_dateCompleted.setText("Completed " + goal.getDateCompleted());

    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_text;
        public TextView tv_dateCompleted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_text = (TextView) itemView.findViewById(R.id.tv_text);
            tv_dateCompleted = (TextView) itemView.findViewById(R.id.tv_dateCompleted);
        }
    }
}