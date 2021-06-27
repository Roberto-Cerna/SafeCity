package com.example.safecity.ui.emergency_contacts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmergencyContactsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public EmergencyContactsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is emergency contacts fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}