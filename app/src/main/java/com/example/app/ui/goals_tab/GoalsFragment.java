package com.example.app.ui.goals_tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.DatabaseHelper;
import com.example.app.Goal;
import com.example.app.GoalsAdapter;
import com.example.app.PastGoalsActivity;
import com.example.app.PastQuizzesActivity;
import com.example.app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GoalsFragment extends Fragment {

    //should all these things be private???

    private GoalsViewModel goalsViewModel;
    EditText et_goal;
    Button btn_addGoal;
    Button btn_pastGoals;

    Button btn_delete;
    Button btn_complete;

    RecyclerView rv_currentGoals;
    RecyclerView.Adapter adapter;

    private List<Goal> goalsList;


    DatabaseHelper databaseHelper;
    String goalText;
    String date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel =
                new ViewModelProvider(this).get(GoalsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_goals, container, false);

        et_goal = root.findViewById(R.id.et_goal);
        btn_addGoal = root.findViewById(R.id.btn_addGoal);
        btn_pastGoals = root.findViewById(R.id.btn_pastGoals);

        btn_complete = root.findViewById(R.id.btn_complete);
        btn_delete = root.findViewById(R.id.btn_delete);

        databaseHelper = new DatabaseHelper(this.getContext());

        rv_currentGoals = (RecyclerView)root.findViewById(R.id.rv_currentGoals);
        rv_currentGoals.setHasFixedSize(true);
        rv_currentGoals.setLayoutManager(new LinearLayoutManager((this.getContext())));

        refreshGoals(); //is it efficient to call this so much?

        goalText = "";

        btn_addGoal.setOnClickListener(v ->{
                try {

                    if (et_goal.getText().toString().trim().equals("")){
                        Toast.makeText(this.getContext(), "must specify input", Toast.LENGTH_SHORT).show();
                    } else {
                        date = getTodayDate();
                        goalText = et_goal.getText().toString();
                        //technically, will we need to change this code because I basically copied it off a tutorial?
                        Goal goal = new Goal(goalText, date, null);
                        Toast.makeText(this.getContext(), goal.getGoalText(), Toast.LENGTH_SHORT).show();
                        databaseHelper = new DatabaseHelper(this.getContext());
                        boolean success = databaseHelper.addGoal(goal);
                        Toast.makeText(this.getContext(), "success " + success, Toast.LENGTH_SHORT).show();
                        refreshGoals();
                        et_goal.setText("");
                        goalText = "";
                    }

                } catch (Exception e) {
                    Toast.makeText(this.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
        });



        btn_pastGoals.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PastGoalsActivity.class);
            startActivity(intent);
        });

       /* btn_delete.setOnClickListener(v-> {
            for (Goal goal: goalsList){
                if (goal.getDateCompleted() != null){
                    Toast.makeText(this.getContext(), "goal has been deleted", Toast.LENGTH_LONG).show();
                    databaseHelper.deleteGoal(goal);
                }
                refreshGoals();
            }

        });

        */

        btn_complete.setOnClickListener(v -> {
            refreshGoals(); //is there a more efficient way to do this, instead of getting the new list of goals every time?
        });





        return root;
    }

    //did I declare this method in the right spot? should it be public or private access modifier?
    private void refreshGoals(){
        goalsList = databaseHelper.getCurrentGoals();
        adapter = new GoalsAdapter(goalsList, this.getContext()); //takes 2 arguments?
        rv_currentGoals.setAdapter(adapter);
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