package com.bigohealth.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.bigohealth.db.DoctorDao;
import com.bigohealth.db.DoctorDatabase;
import com.bigohealth.model.Doctor;
import com.bigohealth.network.ApiEndpoint;
import com.bigohealth.network.ApiService;
import com.bigohealth.network.response.ApiResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DoctorRepository {

    private static final String TAG = "DoctorRepository";
    private DoctorDao doctorDao;
    private ApiEndpoint api;

    private static DoctorRepository REPOSITORY;

    public static DoctorRepository getInstance(Application application) {

        if (REPOSITORY == null) {
            REPOSITORY = new DoctorRepository(application);
        }
        return REPOSITORY;
    }

    private DoctorRepository(Application application) {

        api = ApiService.getRetrofitClient().create(ApiEndpoint.class);
        doctorDao = DoctorDatabase.getDatabase(application).wordDao();
    }

    public void getData(int languageId) {

        api.getDoctors(languageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ApiResponse>() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        doctorDao
                                .upsert(apiResponse.getDoctorsList())
                                .subscribeOn(Schedulers.computation())
                                .subscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }
                });
    }

    public LiveData<List<Doctor>> getDoctorsSpecialization(String specialization) {
        if (specialization.equals("Others")) {
            return doctorDao.getDoctorsSpecializationOther();
        } else {
            return doctorDao.getDoctorsSpecialization(specialization);
        }
    }

    public LiveData<List<Doctor>> getDoctorsList() {
        return doctorDao.getDoctorsList();
    }

    public Doctor getDoctor(String doc_id) {
        return doctorDao.getDoctor(doc_id);
    }
}

