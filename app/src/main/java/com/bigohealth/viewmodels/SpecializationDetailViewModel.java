package com.bigohealth.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bigohealth.model.Doctor;
import com.bigohealth.repository.DoctorRepository;

import java.util.List;

public class SpecializationDetailViewModel extends AndroidViewModel {
    private DoctorRepository repository;
    public LiveData<List<Doctor>> doctorsList = new MutableLiveData<>();

    public SpecializationDetailViewModel(@NonNull Application application) {
        super(application);
        repository = DoctorRepository.getInstance(application);
    }

    public void getData(String specialization) {
        doctorsList = repository.getDoctorsSpecialization(specialization);
    }
}
