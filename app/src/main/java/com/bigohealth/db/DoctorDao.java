package com.bigohealth.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bigohealth.model.Doctor;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface DoctorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable upsert(List<Doctor> doctor);

    @Query("select * from doctor_data")
    LiveData<List<Doctor>> getDoctorsList();

    @Query("select * from doctor_data where docId like :doc_id")
    Doctor getDoctor(String doc_id);

    @Query("select * from doctor_data where specialisation like '%' || :specialization || '%'")
    LiveData<List<Doctor>> getDoctorsSpecialization(String specialization);

    @Query("select * from doctor_data " +
            "where specialisation not like '%Dermatologist%' and " +
            "specialisation not like '%Gynaecology%' and " +
            "specialisation not like '%Paediatrician%' and " +
            "specialisation not like '%General Medicine%' and " +
            "specialisation not like '%Dietician%' and " +
            "specialisation not like '%Dentist%'"
    )
    LiveData<List<Doctor>> getDoctorsSpecializationOther();
}
