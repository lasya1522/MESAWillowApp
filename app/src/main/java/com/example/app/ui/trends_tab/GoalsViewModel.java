package com.example.app.ui.trends_tab;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GoalsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GoalsViewModel() {
        mText = new MutableLiveData<>();
      // mText.setValue("This is stress fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}