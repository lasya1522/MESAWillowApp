package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Emily: yes but why declare redundant strings?

    //NEED TO MAKE THE COLUMN NAMES ETC BETTER: ex: both the daily quiz and the journal table have dates and texts so it's confusing
    //strings for the DAILY_QUIZ_TABLE

    //delete these vars

    public static final String DAILY_QUIZ_TABLE = "DAILY_QUIZ_TABLE";
    public static final String COLUMN_MOOD = "MOOD";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_SLEEP_TIME = "SLEEP_TIME";
    public static final String COLUMN_SLEEP_RATING = "SLEEP_RATING";
    public static final String COLUMN_PRODUCTIVE_TIME = "PRODUCTIVE_TIME";
    public static final String COLUMN_RELAX_TIME= "RELAX_TIME";
    public static final String COLUMN_EXERCISE_TIME = "EXERCISE_TIME";
    public static final String COLUMN_STRESS_LEVEL= "STRESS_LEVEL";
    public static final String COLUMN_STRESSORS = "STRESSORS";
    public static final String COLUMN_OTHER = "OTHER";//don't totally understand these vars

    //strings for the GOAL_TABLE
    public static final String GOAL_TABLE = "GOAL_TABLE";
    public static final String COLUMN_GOAL = "GOAL"; //THINK OF SOME BETTER VARIABLE NAMES
    public static final String COLUMN_DATE_CREATED = "DATE_CREATED";
    public static final String COLUMN_DATE_COMPLETED = "DATE_COMPLETED";

    //strings for the JOURNAL_TABLE
    public static final String JOURNAL_TABLE = "JOURNAL_TABLE";
    public static final String COLUMN_DATE_JOURNAL = "DATE_JOURNAL";
    public static final String COLUMN_TEXT_JOURNAL = "TEXT_JOURNAL";


    //IS THERE A WAY TO SET THIS UP SO I DONT HAVE TO RECREATE THE THING EVERY TIME??!

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Save trial", null, 8); //I had to increment the version number in order to get it to work after adding the date column
        //the onUpgrade code won't be called unless I do that
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //how do I delete our old test data?

        //I CAN'T GET THE ORDER OF THE MOOD AND THE DATE TO CHANGE

        String createDailyQuizTableStatement = "CREATE TABLE DAILY_QUIZ_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, MOOD TEXT, SLEEP_TIME TEXT, SLEEP_RATING TEXT, PRODUCTIVE_TIME TEXT, RELAX_TIME TEXT, " + "EXERCISE_TIME TEXT, STRESS_LEVEL TEXT, STRESSORS TEXT, OTHER TEXT )";
        db.execSQL(createDailyQuizTableStatement);

        String createGoalTableStatement = "CREATE TABLE GOAL_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, GOAL TEXT, DATE_CREATED TEXT, DATE_COMPLETED TEXT)";
        db.execSQL(createGoalTableStatement);

        String createJournalTableStatement = "CREATE TABLE JOURNAL_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE_JOURNAL TEXT, TEXT_JOURNAL TEXT)";
        db.execSQL(createJournalTableStatement);

    }

    //called if the database version number changes. It prevents previous user's apps from breaking when you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // String createGoalTableStatement = "CREATE TABLE GOAL_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, GOAL TEXT, DATE_CREATED TEXT, DATE_COMPLETED TEXT)";
       // db.execSQL(createGoalTableStatement);
        String createJournalTableStatement = "CREATE TABLE JOURNAL_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE_JOURNAL TEXT, TEXT_JOURNAL TEXT)";
        db.execSQL(createJournalTableStatement);

    }

    //methods for JOURNAL_TABLE
    public boolean addJournalEntry(JournalEntry journalEntry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE_JOURNAL, journalEntry.getDate());
        cv.put(COLUMN_TEXT_JOURNAL, journalEntry.getText());
        long insert = db.insert(JOURNAL_TABLE, null, cv);
        //deletethis if-else & the long var once we're sure everything works.
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }

   //methods for DAILY_QUIZ_TABLE
    public boolean addDailyQuiz(DailyQuiz dailyQuiz){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATE, dailyQuiz.getDate());
        cv.put(COLUMN_MOOD, dailyQuiz.getMood());
        cv.put(COLUMN_SLEEP_TIME, dailyQuiz.getSleepTime());
        cv.put(COLUMN_SLEEP_RATING, dailyQuiz.getSleepRating());
        cv.put(COLUMN_PRODUCTIVE_TIME, dailyQuiz.getProductiveTime());
        cv.put(COLUMN_RELAX_TIME, dailyQuiz.getRelaxTime());
        cv.put(COLUMN_EXERCISE_TIME, dailyQuiz.getExerciseTime());
        cv.put(COLUMN_STRESS_LEVEL, dailyQuiz.getStressLevel());
        cv.put(COLUMN_STRESSORS, dailyQuiz.getStressors());
        cv.put(COLUMN_OTHER, dailyQuiz.getOther());

        //tells me whether the item was put into the database successfully
        long insert = db.insert(DAILY_QUIZ_TABLE, null, cv);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }

    public List<DailyQuiz> getDailyQuizData () {
        List<DailyQuiz> returnList = new ArrayList<DailyQuiz>();
        String queryString = "SELECT * FROM " + DAILY_QUIZ_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            // loop through the cursor (result set) and create new DailyQuiz objects. Put them into the return list
            do {
                //should i change the order of the stuff because the date is second and that's kind of annoying? should I make it second

                //I CAN'T GET  THE DATE AND THE MOOD TO SWITCH COLUMN ORDER
                String dailyQuizMood = cursor.getString(2);
                String dailyQuizDate = cursor.getString(1);
                int dailyQuizSleepTime = cursor.getInt(3);
                String dailyQuizSleepRating = cursor.getString(4);
                int dailyQuizProductiveTime = cursor.getInt(5);
                int dailyQuizRelaxTime = cursor.getInt(6);
                int dailyQuizExerciseTime = cursor.getInt(7);
                String dailyQuizStressLevel = cursor.getString(8);
                String dailyQuizStressors = cursor.getString(9);
                String dailyQuizOther = cursor.getString(10);

                DailyQuiz quiz = new DailyQuiz(dailyQuizDate, dailyQuizMood, dailyQuizSleepTime, dailyQuizSleepRating, dailyQuizProductiveTime, dailyQuizRelaxTime, dailyQuizExerciseTime, dailyQuizStressLevel, dailyQuizStressors, dailyQuizOther  );
                returnList.add(quiz);
            } while(cursor.moveToNext());

        } else {
            //failure. do not add anything to the list
        }

        return returnList;
    }
    public DailyQuiz getDailyQuiz (String date){
        SQLiteDatabase db = this.getReadableDatabase();
        DailyQuiz returnQuiz = new DailyQuiz("", "", -1, "", -1, -1, -1, "", "", "");
        String queryString = " select * from " + DAILY_QUIZ_TABLE + " where " + COLUMN_DATE + " = \"" +  date + "\"";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                String mood = cursor.getString(2);
                int sleepTime = cursor.getInt(3);
                String sleepRating = cursor.getString(4);
                int productiveTime = cursor.getInt(5);
                int relaxTime = cursor.getInt(6);
                int exerciseTime = cursor.getInt(7);
                String stressLevel = cursor.getString(8);
                String stressors = cursor.getString(9);
                String other = cursor.getString(10);
                returnQuiz = new DailyQuiz(date, mood, sleepTime, sleepRating, productiveTime, relaxTime, exerciseTime, stressLevel, stressors, other);

            } while(cursor.moveToNext());
        } else {
            //failure. do not add anything to the list
        }

        return returnQuiz;
    }


    //Methods for GOAL_TABLE
    public boolean addGoal(Goal goal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_GOAL, goal.getGoalText());
        cv.put(COLUMN_DATE_CREATED, goal.getDateCreated());
        cv.put(COLUMN_DATE_COMPLETED, goal.getDateCompleted());

        //tells me whether the item was put into the database successfully
        long insert = db.insert(GOAL_TABLE, null, cv);
        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }
    public void completeGoal(String goalText, String dateCompleted){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" update " + GOAL_TABLE + " set " + COLUMN_DATE_COMPLETED + " = \"" + dateCompleted + "\" where " + COLUMN_GOAL + " =  \"" + goalText + "\"");

    }

    public List<Goal> getCurrentGoals () {
        List<Goal> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + GOAL_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){

            do {
                if (cursor.getString(3) == null){
                    String goalText = cursor.getString(1);
                    String dateCreated = cursor.getString(2);
                    String dateCompleted = cursor.getString(3);

                    Goal goal = new Goal(goalText, dateCreated, dateCompleted);
                    returnList.add(goal);
                }
            } while(cursor.moveToNext());

        } else {
            //failure. do not add anything to the list
        }

        return returnList;
    }

    public List<Goal> getCompletedGoals() {
        List<Goal> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + GOAL_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {
                if (cursor.getString(3) != null) {
                    String goalText = cursor.getString(1);
                    String dateCreated = cursor.getString(2);
                    String dateCompleted = cursor.getString(3);

                    Goal goal = new Goal(goalText, dateCreated, dateCompleted);
                    returnList.add(goal);
                }
            } while(cursor.moveToNext());

        } else {
            //failure. do not add anything to the list
        }

        return returnList;

    }

    //clear data method-- clears BOTH tables
    public void deleteAll () {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryStringDailyQuiz = "DELETE FROM " + DAILY_QUIZ_TABLE;
        String queryStringGoal = "DELETE FROM " + GOAL_TABLE;
        Cursor cursor = db.rawQuery(queryStringDailyQuiz, null);
        do {
        } while(cursor.moveToNext());
        Cursor cursor2 = db.rawQuery(queryStringGoal, null);
        do {
        } while(cursor2.moveToNext());
    }
}
