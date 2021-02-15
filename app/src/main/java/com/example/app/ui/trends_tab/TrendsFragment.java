package com.example.app.ui.trends_tab;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.DailyQuiz;
import com.example.app.DatabaseHelper;
import com.example.app.GoalsAdapter;
import com.example.app.OtherAdapter;
import com.example.app.R;
import com.example.app.StressorsAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TrendsFragment extends Fragment {

    private TrendsViewModel trendsViewModel;
    //create references to buttons
    Button submitBtn;
    Button saveBtn;
    Button btn_viewPastQuizzes; //Previous quiz button

    TextView tv_sleepTimeData;
    TextView tv_productiveTimeData;
    TextView tv_relaxTimeData;
    TextView tv_exerciseTimeData;

    PieChart chart_mood;
    PieChart chart_sleepRating;
    PieChart chart_stressLevel;

    BarChart chart_sleepTime;
    BarChart chart_productiveTime;
    BarChart chart_relaxTime;
    BarChart chart_exerciseTime;

    RecyclerView rv_stressors;
    RecyclerView rv_other;

    RecyclerView.Adapter stressorsAdapter;
    RecyclerView.Adapter otherAdapter;

    DatabaseHelper databaseHelper;
    List<DailyQuiz> dailyQuizData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //******* VARIABLE INITIALIZATIONS ******* //
        trendsViewModel = new ViewModelProvider(this).get(TrendsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_trends, container, false);

        databaseHelper = new DatabaseHelper(this.getContext()); //is this.getContext() safe to use? I guessed.

        tv_sleepTimeData = root.findViewById(R.id.tv_sleepTimeData);
        tv_productiveTimeData = root.findViewById(R.id.tv_productiveTimeData);
        tv_relaxTimeData = root.findViewById(R.id.tv_relaxTimeData);
        tv_exerciseTimeData = root.findViewById(R.id.tv_exerciseTimeData);

        chart_mood = root.findViewById(R.id.chart_mood);
        chart_sleepRating = root.findViewById(R.id.chart_sleepRating);
        chart_stressLevel = root.findViewById(R.id.chart_stressLevel);

        chart_sleepTime = root.findViewById(R.id.chart_sleepTime);
        chart_productiveTime = root.findViewById(R.id.chart_productiveTime);
        chart_relaxTime = root.findViewById(R.id.chart_relaxTime);
        chart_exerciseTime = root.findViewById(R.id.chart_exerciseTime);

        rv_stressors = root.findViewById(R.id.rv_stressors);
        rv_stressors.setHasFixedSize(true);
        rv_stressors.setLayoutManager(new LinearLayoutManager((this.getContext())));

        rv_other = root.findViewById(R.id.rv_other);
        rv_other.setHasFixedSize(true);
        rv_other.setLayoutManager(new LinearLayoutManager((this.getContext())));

        dailyQuizData = databaseHelper.getDailyQuizData();

        //******** RETRIEVING DATA *******//

        List<PieEntry> moodEntries = new ArrayList<>();
        ArrayList<String> moodList = new ArrayList<>();

        List<PieEntry> sleepRatingEntries = new ArrayList<>();
        ArrayList<String> sleepRatingList = new ArrayList<>();

        List<PieEntry> stressLevelEntries = new ArrayList<>();
        ArrayList<String> stressLevelList = new ArrayList<>();

        List<BarEntry> sleepTimeEntries = new ArrayList<>();
        ArrayList<Integer> sleepTimeList = new ArrayList<>();

        List<BarEntry> productiveTimeEntries = new ArrayList<>();
        ArrayList<Integer> productiveTimeList = new ArrayList<>();

        List<BarEntry> relaxTimeEntries = new ArrayList<>();
        ArrayList<Integer> relaxTimeList = new ArrayList<>();

        List<BarEntry> exerciseTimeEntries = new ArrayList<>();
        ArrayList<Integer> exerciseTimeList = new ArrayList<>();

        final String [] dates = {"", "", "", "", "", "", ""};
        if (dailyQuizData.size() == 0){

            chart_mood.setNoDataText("No data available. Complete the daily quiz!");
            chart_sleepRating.setNoDataText("No data available. Complete the daily quiz!");
            chart_stressLevel.setNoDataText("No data available. Complete the daily quiz!");
            chart_sleepTime.setNoDataText("No data available. Complete the daily quiz!");
            chart_productiveTime.setNoDataText("No data available. Complete the daily quiz!");
            chart_relaxTime.setNoDataText("No data available. Complete the daily quiz!");
            chart_exerciseTime.setNoDataText("No data available. Complete the daily quiz!");

            chart_mood.setNoDataTextColor(R.color.scheme_purple_1);
            chart_sleepRating.setNoDataTextColor(R.color.scheme_purple_1);
            chart_stressLevel.setNoDataTextColor(R.color.scheme_purple_1);
            chart_sleepTime.setNoDataTextColor(R.color.scheme_purple_1);
            chart_productiveTime.setNoDataTextColor(R.color.scheme_purple_1);
            chart_relaxTime.setNoDataTextColor(R.color.scheme_purple_1);
            chart_exerciseTime.setNoDataTextColor(R.color.scheme_purple_1);

        } else {

            if (dailyQuizData.size() > 6) {

                for (int i = 6; i >= 0; i--) {
                    moodList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getMood());

                    sleepRatingList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getSleepRating());

                    stressLevelList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getStressLevel());

                    sleepTimeEntries.add(new BarEntry(6-i, dailyQuizData.get(dailyQuizData.size() - 1 - i).getSleepTime()));
                    sleepTimeList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getSleepTime());

                    productiveTimeEntries.add(new BarEntry(6-i, dailyQuizData.get(dailyQuizData.size() - 1 - i).getProductiveTime()));
                    productiveTimeList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getProductiveTime());

                    relaxTimeEntries.add(new BarEntry(6-i, dailyQuizData.get(dailyQuizData.size() - 1 - i).getRelaxTime()));
                    relaxTimeList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getRelaxTime());

                    exerciseTimeEntries.add(new BarEntry(6-i, dailyQuizData.get(dailyQuizData.size() - 1 - i).getExerciseTime()));
                    exerciseTimeList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getExerciseTime());
                    String date = dailyQuizData.get(dailyQuizData.size() - 1 - i).getDate();
                    String noYearDate = date.substring(0, date.length() - 5);
                    dates[i] = noYearDate; // TEST & MAKE SURE THE DATES ARE WORKING RIGHT (check that the dates are corresponding with the correct bars

                }

            } else {

                for (int i = dailyQuizData.size()-1; i >= 0; i--) {
                    moodList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getMood());

                    sleepRatingList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getSleepRating());

                    stressLevelList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getStressLevel());

                    sleepTimeEntries.add(new BarEntry(6-i, dailyQuizData.get(dailyQuizData.size() - 1 - i).getSleepTime()));
                    sleepTimeList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getSleepTime());

                    productiveTimeEntries.add(new BarEntry(6-i, dailyQuizData.get(dailyQuizData.size() - 1 - i).getProductiveTime()));
                    productiveTimeList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getProductiveTime());

                    relaxTimeEntries.add(new BarEntry(6-i, dailyQuizData.get(dailyQuizData.size() - 1 - i).getRelaxTime()));
                    relaxTimeList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getRelaxTime());

                    exerciseTimeEntries.add(new BarEntry(6-i, dailyQuizData.get(dailyQuizData.size() - 1 - i).getExerciseTime()));
                    exerciseTimeList.add(dailyQuizData.get(dailyQuizData.size() - 1 - i).getExerciseTime());
                    String date = dailyQuizData.get(dailyQuizData.size() - 1 - i).getDate();
                    String noYearDate = date.substring(0, date.length() - 5);
                    dates[i] = noYearDate; // TEST & MAKE SURE THE DATES ARE WORKING RIGHT (check that the dates are corresponding with the correct bars

                }

            }
            List<Integer> moodFreqData = getFreq(moodList, "Good", "Decent", "Bad");
            if (moodFreqData.get(0) != 0) moodEntries.add(new PieEntry(moodFreqData.get(0), "Good"));
            if (moodFreqData.get(1) != 0) moodEntries.add(new PieEntry(moodFreqData.get(1), "Decent"));
            if (moodFreqData.get(2) != 0) moodEntries.add(new PieEntry(moodFreqData.get(2), "Bad"));
            PieDataSet moodSet = new PieDataSet(moodEntries, "Mood");
            PieData moodData = new PieData(moodSet);

            List<Integer> sleepRatingFreqData = getFreq(sleepRatingList, "Good", "Decent", "Bad");
            if (sleepRatingFreqData.get(0) != 0) sleepRatingEntries.add(new PieEntry(sleepRatingFreqData.get(0), "Good"));
            if (sleepRatingFreqData.get(1) != 0)sleepRatingEntries.add(new PieEntry(sleepRatingFreqData.get(1), "Decent"));
            if (sleepRatingFreqData.get(2) != 0)sleepRatingEntries.add(new PieEntry(sleepRatingFreqData.get(2), "Bad"));
            PieDataSet sleepRatingSet = new PieDataSet(sleepRatingEntries, "Sleep Rating");
            PieData sleepRatingData = new PieData(sleepRatingSet);

            List<Integer> stressLevelFreqData = getFreq(stressLevelList, "High", "Moderate", "Low");
            if (stressLevelFreqData.get(0) != 0)stressLevelEntries.add(new PieEntry(stressLevelFreqData.get(0), "High"));
            if (stressLevelFreqData.get(1) != 0)stressLevelEntries.add(new PieEntry(stressLevelFreqData.get(1), "Moderate"));
            if (stressLevelFreqData.get(2) != 0)stressLevelEntries.add(new PieEntry(stressLevelFreqData.get(2), "Low"));
            PieDataSet stressLevelSet = new PieDataSet(stressLevelEntries, "Stress Level");
            PieData stressLevelData = new PieData(stressLevelSet);


            BarDataSet sleepTimeSet = new BarDataSet(sleepTimeEntries, "Sleep Time");
            BarData sleepTimeData = new BarData(sleepTimeSet);

            BarDataSet productiveTimeSet = new BarDataSet(productiveTimeEntries, "Sleep Time");
            BarData productiveTimeData = new BarData(productiveTimeSet);

            BarDataSet relaxTimeSet = new BarDataSet(relaxTimeEntries, "Relax Time");
            BarData relaxTimeData = new BarData(relaxTimeSet);

            BarDataSet exerciseTimeSet = new BarDataSet(exerciseTimeEntries, "Exercise Time");
            BarData exerciseTimeData = new BarData(exerciseTimeSet);

            double sleepTimeMean = calculateMean(sleepTimeList);
            double sleepTimeMedian = calculateMedian(sleepTimeList);
            double sleepTimeMode = calculateMode(sleepTimeList);
            double sleepTimeRange = calculateRange(sleepTimeList);

            double productiveTimeMean = calculateMean(productiveTimeList);
            double productiveTimeMedian = calculateMedian(productiveTimeList);
            double productiveTimeMode = calculateMode(productiveTimeList);
            double productiveTimeRange = calculateRange(productiveTimeList);

            double relaxTimeMean = calculateMean(relaxTimeList);
            double relaxTimeMedian = calculateMedian(relaxTimeList);
            double relaxTimeMode = calculateMode(relaxTimeList);
            double relaxTimeRange = calculateRange(relaxTimeList);

            double exerciseTimeMean = calculateMean(exerciseTimeList);
            double exerciseTimeMedian = calculateMedian(exerciseTimeList);
            double exerciseTimeMode = calculateMode(exerciseTimeList);
            double exerciseTimeRange = calculateRange(exerciseTimeList);

            //********* FORMATTING *********//

            // ----- 1) Mood PieChart Formatting------
            Description moodDescription = chart_mood.getDescription();
            moodDescription.setEnabled(false);

            Legend moodLegend = chart_mood.getLegend();
            moodLegend.setEnabled(false);

            moodSet.setColors(new int[]{R.color.mood_color2, R.color.mood_color3, R.color.mood_color4}, this.getContext());
            moodSet.setDrawValues(false);

            chart_mood.setHoleRadius(0);
            chart_mood.setTransparentCircleAlpha(0);

            // ----- 2) SleepRating PieChart Formatting----
            Description sleepRatingDescription = chart_sleepRating.getDescription();
            sleepRatingDescription.setEnabled(false);

            Legend sleepRatingLegend = chart_sleepRating.getLegend();
            sleepRatingLegend.setEnabled(false);

            sleepRatingSet.setColors(new int[]{R.color.sleep_color2, R.color.sleep_color3, R.color.sleep_color4}, this.getContext());
            sleepRatingSet.setDrawValues(false);

            chart_sleepRating.setHoleRadius(0);
            chart_sleepRating.setTransparentCircleAlpha(0);


            // ---- 3) StressLevel PieChart Formatting----
            Description stressLevelDescription = chart_stressLevel.getDescription();
            stressLevelDescription.setEnabled(false);

            Legend stressLevelLegend = chart_stressLevel.getLegend();
            stressLevelLegend.setEnabled(false);

            stressLevelSet.setColors(new int[]{R.color.stress_color2, R.color.stress_color3, R.color.stress_color4}, this.getContext());
            stressLevelSet.setDrawValues(false);

            chart_stressLevel.setHoleRadius(0);
            chart_stressLevel.setTransparentCircleAlpha(0);


            // ----- 4) SleepTime BarChart Formatting-----
            Description sleepTimeDescription = chart_sleepTime.getDescription();
            sleepTimeDescription.setEnabled(false);

            Legend sleepTimeLegend = chart_sleepTime.getLegend();
            sleepTimeLegend.setEnabled(false);

            sleepTimeSet.setColors(new int[]{R.color.sleep_color2, R.color.sleep_color3, R.color.sleep_color4}, this.getContext());
            sleepTimeSet.setDrawValues(false);

            chart_sleepTime.setEnabled(false);
            chart_sleepTime.setTouchEnabled(false);
            chart_sleepTime.setDragEnabled(false);
            chart_sleepTime.setScaleXEnabled(false);
            chart_sleepTime.setScaleYEnabled(false);
            chart_sleepTime.setScaleEnabled(false);
            chart_sleepTime.setPinchZoom(false);
            chart_sleepTime.setDoubleTapToZoomEnabled(false);
            chart_sleepTime.setHighlightPerDragEnabled(false);
            chart_sleepTime.setHighlightPerTapEnabled(false);
            chart_sleepTime.setDrawGridBackground(false);
            chart_sleepTime.setData(sleepTimeData);
            chart_sleepTime.setDrawValueAboveBar(false);
            chart_sleepTime.setFitBars(true);

            //chart_sleepTime.animateXY(1000, 1000); //cool animation we can maybe add later

            XAxis xaxis_sleepTime = chart_sleepTime.getXAxis();
            xaxis_sleepTime.setDrawGridLines(false);
            xaxis_sleepTime.setDrawAxisLine(true);
            xaxis_sleepTime.setDrawAxisLine(true);
            xaxis_sleepTime.setDrawGridLines(false);
            xaxis_sleepTime.setAxisMinimum(-0.5f);
            xaxis_sleepTime.setAxisMaximum(6.5f);
            Toast.makeText(this.getContext(), String.valueOf(dates.length), Toast.LENGTH_LONG).show();

            ValueFormatter formatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    return dates[6 - (int)value];
                }
            };
            xaxis_sleepTime.setValueFormatter(formatter);
            YAxis left_axis_sleepTime = chart_sleepTime.getAxisLeft();
            left_axis_sleepTime.setDrawGridLines(true);
            left_axis_sleepTime.setDrawAxisLine(true);
            left_axis_sleepTime.setDrawLabels(true);
            left_axis_sleepTime.setAxisMinimum(0f);
            //  left_axis_sleepTime.setAxisMaximum(24f);

            YAxis right_axis_sleepTime = chart_sleepTime.getAxisRight();
            right_axis_sleepTime.setDrawGridLines(false);
            right_axis_sleepTime.setDrawAxisLine(true);
            right_axis_sleepTime.setDrawLabels(false);
            left_axis_sleepTime.setAxisMinimum(0f);
            // right_axis_sleepTime.setAxisMaximum(24f);

            //----- 5) ProductiveTime BarChart Formatting -------
            Description productiveTimeDescription = chart_productiveTime.getDescription();
            productiveTimeDescription.setEnabled(false);

            Legend productiveTimeLegend = chart_productiveTime.getLegend();
            productiveTimeLegend.setEnabled(false);

            productiveTimeSet.setColors(new int[]{R.color.productive_color2, R.color.productive_color3, R.color.productive_color4}, this.getContext());
            productiveTimeSet.setDrawValues(false);

            chart_productiveTime.setEnabled(false);
            chart_productiveTime.setTouchEnabled(false);
            chart_productiveTime.setDragEnabled(false);
            chart_productiveTime.setScaleXEnabled(false);
            chart_productiveTime.setScaleYEnabled(false);
            chart_productiveTime.setScaleEnabled(false);
            chart_productiveTime.setPinchZoom(false);
            chart_productiveTime.setDoubleTapToZoomEnabled(false);
            chart_productiveTime.setHighlightPerDragEnabled(false);
            chart_productiveTime.setHighlightPerTapEnabled(false);
            chart_productiveTime.setDrawGridBackground(false);
            chart_productiveTime.setData(productiveTimeData);
            chart_productiveTime.setFitBars(true);
            chart_productiveTime.setDrawValueAboveBar(false);
            chart_productiveTime.invalidate(); // refresh

            XAxis xaxis_productiveTime = chart_productiveTime.getXAxis();
            xaxis_productiveTime.setDrawGridLines(false);
            xaxis_productiveTime.setDrawAxisLine(true);
            xaxis_productiveTime.setDrawAxisLine(true);
            xaxis_productiveTime.setDrawGridLines(false);
            xaxis_productiveTime.setAxisMinimum(-0.5f);
            xaxis_productiveTime.setAxisMaximum(6.5f);

            xaxis_productiveTime.setValueFormatter(formatter);
            YAxis left_axis_productiveTime = chart_productiveTime.getAxisLeft();
            left_axis_productiveTime.setDrawGridLines(true);
            left_axis_productiveTime.setDrawAxisLine(true);
            left_axis_productiveTime.setDrawLabels(true);
            left_axis_productiveTime.setAxisMinimum(0f); // is this necessary? will it cut off part of the thing
            //  left_axis_sleepTime.setAxisMaximum(24f);

            YAxis right_axis_productiveTime = chart_productiveTime.getAxisRight();
            right_axis_productiveTime.setDrawGridLines(false);
            right_axis_productiveTime.setDrawAxisLine(true);
            right_axis_productiveTime.setDrawLabels(false);
            left_axis_productiveTime.setAxisMinimum(0f);
            // right_axis_sleepTime.setAxisMaximum(24f);

            //----- 6) RelaxTime BarChart Formatting -------
            Description relaxTimeDescription = chart_relaxTime.getDescription();
            relaxTimeDescription.setEnabled(false);

            Legend relaxTimeLegend = chart_relaxTime.getLegend();
            relaxTimeLegend.setEnabled(false);

            relaxTimeSet.setColors(new int[]{R.color.relax_color2, R.color.relax_color3, R.color.relax_color4}, this.getContext());
            relaxTimeSet.setDrawValues(false);

            chart_relaxTime.setEnabled(false);
            chart_relaxTime.setTouchEnabled(false);
            chart_relaxTime.setDragEnabled(false);
            chart_relaxTime.setScaleXEnabled(false);
            chart_relaxTime.setScaleYEnabled(false);
            chart_relaxTime.setScaleEnabled(false);
            chart_relaxTime.setPinchZoom(false);
            chart_relaxTime.setDoubleTapToZoomEnabled(false);
            chart_relaxTime.setHighlightPerDragEnabled(false);
            chart_relaxTime.setHighlightPerTapEnabled(false);
            chart_relaxTime.setDrawGridBackground(false);
            chart_relaxTime.setData(relaxTimeData);
            chart_relaxTime.setDrawValueAboveBar(false);
            chart_relaxTime.setFitBars(true);

            XAxis xaxis_relaxTime = chart_relaxTime.getXAxis();
            xaxis_relaxTime.setDrawGridLines(false);
            xaxis_relaxTime.setDrawAxisLine(true);
            xaxis_relaxTime.setDrawAxisLine(true);
            xaxis_relaxTime.setDrawGridLines(false);
            xaxis_relaxTime.setAxisMinimum(-0.5f);
            xaxis_relaxTime.setAxisMaximum(6.5f);
            xaxis_relaxTime.setValueFormatter(formatter);

            YAxis left_axis_relaxTime = chart_relaxTime.getAxisLeft();
            left_axis_relaxTime.setDrawGridLines(true);
            left_axis_relaxTime.setDrawAxisLine(true);
            left_axis_relaxTime.setDrawLabels(true);
            left_axis_relaxTime.setAxisMinimum(0f); // is this necessary? will it cut off part of the thing
            //  left_axis_sleepTime.setAxisMaximum(24f);

            YAxis right_axis_relaxTime = chart_relaxTime.getAxisRight();
            right_axis_relaxTime.setDrawGridLines(false);
            right_axis_relaxTime.setDrawAxisLine(true);
            right_axis_relaxTime.setDrawLabels(false);
            left_axis_relaxTime.setAxisMinimum(0f);
            // right_axis_sleepTime.setAxisMaximum(24f);

            // ------ 7) ExerciseTime BarChart Formatting -----
            Description exerciseTimeDescription = chart_exerciseTime.getDescription();
            exerciseTimeDescription.setEnabled(false);

            Legend exerciseTimeLegend = chart_exerciseTime.getLegend();
            exerciseTimeLegend.setEnabled(false);

            exerciseTimeSet.setColors(new int[]{R.color.exercise_color2, R.color.exercise_color3, R.color.exercise_color4}, this.getContext());
            // this is the color scheme for the graphs
            exerciseTimeSet.setDrawValues(false);

            chart_exerciseTime.setEnabled(false);
            chart_exerciseTime.setTouchEnabled(false);
            chart_exerciseTime.setDragEnabled(false);
            chart_exerciseTime.setScaleXEnabled(false);
            chart_exerciseTime.setScaleYEnabled(false);
            chart_exerciseTime.setScaleEnabled(false);
            chart_exerciseTime.setPinchZoom(false);
            chart_exerciseTime.setDoubleTapToZoomEnabled(false);
            chart_exerciseTime.setHighlightPerDragEnabled(false);
            chart_exerciseTime.setHighlightPerTapEnabled(false);
            chart_exerciseTime.setDrawGridBackground(false);
            chart_exerciseTime.setData(exerciseTimeData);
            chart_exerciseTime.setFitBars(true);
            chart_exerciseTime.setDrawValueAboveBar(false);

            XAxis xaxis_exerciseTime = chart_exerciseTime.getXAxis();
            xaxis_exerciseTime.setDrawGridLines(false);
            xaxis_exerciseTime.setDrawAxisLine(true);
            xaxis_exerciseTime.setDrawAxisLine(true);
            xaxis_exerciseTime.setDrawGridLines(false);
            xaxis_exerciseTime.setAxisMinimum(-0.5f);
            xaxis_exerciseTime.setAxisMaximum(6.5f);
            xaxis_exerciseTime.setValueFormatter(formatter);

            YAxis left_axis_exerciseTime = chart_exerciseTime.getAxisLeft();
            left_axis_exerciseTime.setDrawGridLines(true);
            left_axis_exerciseTime.setDrawAxisLine(true);
            left_axis_exerciseTime.setDrawLabels(true);
            left_axis_exerciseTime.setAxisMinimum(0f); // is this necessary? will it cut off part of the thing
            //  left_axis_sleepTime.setAxisMaximum(24f);

            YAxis right_axis_exerciseTime = chart_exerciseTime.getAxisRight();
            right_axis_exerciseTime.setDrawGridLines(false);
            right_axis_exerciseTime.setDrawAxisLine(true);
            right_axis_exerciseTime.setDrawLabels(false);
            left_axis_exerciseTime.setAxisMinimum(0f);
            // right_axis_sleepTime.setAxisMaximum(24f);

            //********** SETTING DATA **********

            chart_mood.setData(moodData);
            chart_mood.invalidate();

            chart_sleepRating.setData(sleepRatingData);
            chart_sleepRating.invalidate();

            chart_stressLevel.setData(stressLevelData);
            chart_stressLevel.invalidate();

            //DecimalFormat decimalFormat = new DecimalFormat("##.##"); // does this round or simply truncate??????

            chart_sleepTime.setData(sleepTimeData);
            chart_sleepTime.invalidate();
            tv_sleepTimeData.setText("Mean = " + round(sleepTimeMean) + " hours \n"
                    + "Median = " + round(sleepTimeMedian) + " hours \n" +
                    "Mode = " + round(sleepTimeMode) + " hours \n" +
                    "Range = " + round(sleepTimeRange) + " hours \n" +
                    "");

            chart_productiveTime.setData(productiveTimeData);
            chart_productiveTime.invalidate();
            tv_productiveTimeData.setText("Mean = " + round(productiveTimeMean) + " hours \n" +
                    "Median = " + round(productiveTimeMedian) + " hours \n" +
                    "Mode = " + round(productiveTimeMode) + " hours \n" +
                    "Range = " + round(productiveTimeRange) + " hours \n" +
                    "");


            chart_relaxTime.setData(relaxTimeData);
            chart_relaxTime.invalidate();
            tv_relaxTimeData.setText("Mean = " + round(relaxTimeMean) + " hours \n" +
                    "Median = " + round(relaxTimeMedian) + " hours \n" +
                    "Mode = " + round(relaxTimeMode) + " hours \n" +
                    "Range = " + round(relaxTimeRange) + " hours \n" +
                    "");


            chart_exerciseTime.setData(exerciseTimeData);
            chart_exerciseTime.invalidate();
            tv_exerciseTimeData.setText("Mean = " + round(exerciseTimeMean) + " hours \n" +
                    "Median = " + round(exerciseTimeMedian) + " hours \n" +
                    "Mode = " + round(exerciseTimeMode) + " hours \n" +
                    "Range = " + round(exerciseTimeRange) + " hours \n" +
                    "");

            stressorsAdapter = new StressorsAdapter(dailyQuizData, this.getContext()); //takes 2 arguments?
            rv_stressors.setAdapter(stressorsAdapter);

            otherAdapter = new OtherAdapter(dailyQuizData, this.getContext());
            rv_other.setAdapter(otherAdapter);

        }
        return root;
    }

    private double calculateMean(ArrayList<Integer> list ){
        double mean = 0;
        double sleepTimeMean = 0;
        for (int i = 0; i < list.size(); i++){
            mean += list.get(i);
        }
        mean /= list.size();
        return mean;
    }

    //NOT SURE THIS MEDIAN CALCULATOR IS ACTUALLY OKAY-- SOME ISSUES
    private double calculateMedian(ArrayList<Integer> list){
        //TEST AND MAKE SURE THIS ACTUALLY WORKS
        double median = 0;
        Collections.sort(list);
        if (list.size() % 2 == 0) {
            median = ((list.get(list.size()/2) + list.get(list.size()/2-1)))/2.0; //check to make sure this line actually works. integer divisi
        } else {
            median = list.get(list.size() / 2) ;
        }
        return median;
    }

   private int calculateMode(ArrayList<Integer> list){
        // ********************
       // do we need to account for sets with multiple modes???????????????
       int mode = 0;
       int max = Integer.MIN_VALUE;
        int[] freq = new int[list.size()];
        for (int i = 0; i < list.size(); i++){
            int counter = 0;
            for (int j = 0; j < list.size(); j ++){
                if (list.get(i) == list.get(j)){
                    counter++;
                }
            }
            if (counter > max){
                max = counter;
                mode = list.get(i);
            }

        }
        return mode;
   }

   private int calculateRange(ArrayList<Integer> list){
        int range = 0;
        Collections.sort(list);
        range = list.get(list.size()-1) - list.get(0);
        return range;
   }

   private double round(double numb) {
       numb = Math.round(numb * 100.0) / 100.0;
       return numb;
   }

   /*
    Gets frequencies for the Pie Chart for mood and for sleep rating
    */
   private ArrayList<Integer> getFreq(ArrayList<String> list, String option1, String option2, String option3){
        ArrayList<Integer> counter = new ArrayList<>();
        counter.add(0);
        counter.add(0);
        counter.add(0);
        counter.add(0);

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals(option1)){
                counter.set(0, counter.get(0)+1);
                Toast.makeText(this.getContext(), "counter.get(0) == " + counter.get(0), Toast.LENGTH_SHORT).show();
            } else if (list.get(i).equals(option2)){
                counter.set(1, counter.get(1)+1);
            } else if (list.get(i).equals(option3)){
                counter.set(2, counter.get(2)+1);
            } else {
               Toast.makeText(this.getContext(), "not valid String", Toast.LENGTH_SHORT).show();
            }
        }
        counter.set(0, counter.get(0));
        Toast.makeText(this.getContext(), counter.get(0).toString(), Toast.LENGTH_SHORT).show();
        counter.set(1, counter.get(1));
        counter.set(2, counter.get(2));
        counter.set(3, counter.get(3));

       return counter;
    }

    }