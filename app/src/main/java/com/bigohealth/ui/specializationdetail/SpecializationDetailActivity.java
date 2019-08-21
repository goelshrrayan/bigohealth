package com.bigohealth.ui.specializationdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.bigohealth.R;
import com.bigohealth.model.Doctor;
import com.bigohealth.viewmodels.SpecializationDetailViewModel;

import java.util.List;

public class SpecializationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialization_detail);

        SpecializationDetailViewModel viewModel = ViewModelProviders.of(this).get(SpecializationDetailViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.rv_doctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String specialization = getIntent().getStringExtra("TEMP");

        setTitle(specialization);

        viewModel.getData(specialization);
        viewModel.doctorsList.observe(this, doctors -> {
            recyclerView.setAdapter(new DoctorsSpecializationList(doctors, this));
        });
    }
}
