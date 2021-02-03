package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {
    private List<Goal> goals;
    private Context context;

    public GoalsAdapter(List<Goal> goals, Context context ){
        this.goals = goals;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.list_item_goal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Goal goal = goals.get(position);

    holder.checkBox.setText(goal.getGoalText());
        holder.checkBox.setOnClickListener(v -> {
           String date = getTodayDate();
           DatabaseHelper databaseHelper = new DatabaseHelper(context);
           databaseHelper.completeGoal(holder.checkBox.getText().toString(), date);
          // holder.checkBox.setVisibility(View.GONE);
           //List<Goal> goalsList = databaseHelper.getCurrentGoals();
           //RecyclerView.Adapter adapter = new GoalsAdapter(goalsList, context); //takes 2 arguments
           // rv_currentGoals.setAdapter(adapter);
        });

    }


    @Override
    public int getItemCount() {
        return goals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       public CheckBox checkBox;
       public LinearLayout linearLayoutCurrentGoals;
       public DatabaseHelper databaseHelper;
       public Context context;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            checkBox = (CheckBox) itemView.findViewById(R.id.cb);
            linearLayoutCurrentGoals = (LinearLayout) itemView.findViewById((R.id.linearLayoutCurrentGoals));
        }
    }
    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int year = cal.get(Calendar.YEAR);
        return makeDateString(day, month, year);
    }
    private String makeDateString(int dayOfMonth, int month, int year) {
        return (month + "-" + dayOfMonth + "-" + year);
    }
}
