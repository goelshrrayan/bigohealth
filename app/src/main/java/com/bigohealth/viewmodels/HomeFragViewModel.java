package com.bigohealth.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigohealth.model.Doctor;
import com.bigohealth.repository.DoctorRepository;

import java.util.List;

public class HomeFragViewModel extends AndroidViewModel {

    private DoctorRepository repository;
    public LiveData<List<Doctor>> doctorsList = new MutableLiveData<>();

    public HomeFragViewModel(@NonNull Application application) {
        super(application);
        repository = DoctorRepository.getInstance(application);
    }

    public void getData() {
        try {
            repository.getData(1);  //TODO: change as per language
        } catch (Exception e) {
            Log.d("TAG_TAG_TAG", e.getMessage());
        }
        doctorsList = repository.getDoctorsList();
    }
}
