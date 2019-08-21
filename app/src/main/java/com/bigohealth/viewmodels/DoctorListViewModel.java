package com.bigohealth.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bigohealth.model.Doctor;
import com.bigohealth.repository.DoctorRepository;

import java.util.List;

public class DoctorListViewModel extends AndroidViewModel {

    private LiveData<List<Doctor>> doctorsList;
    DoctorRepository repository;
    Doctor doctor;

    public DoctorListViewModel(@NonNull Application application) {
        super(application);
        repository = DoctorRepository.getInstance(application);
        doctorsList = repository.getDoctorsList();

    }

    public Doctor getDoctor(String doc_id) {
        return repository.getDoctor(doc_id);
    }

    public LiveData<List<Doctor>> getDoctorsList() {
        return doctorsList;
    }

    public LiveData<List<Doctor>> getData(String specialization) {
        return repository.getDoctorsSpecialization(specialization);
    }
}
