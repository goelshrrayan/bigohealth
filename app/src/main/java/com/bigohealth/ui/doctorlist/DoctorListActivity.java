package com.bigohealth.ui.doctorlist;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigohealth.R;
import com.bigohealth.viewmodels.DoctorListViewModel;

import static com.bigohealth.utils.Constant.INTENT_EXTRA_SPECIALIZATION;

public class DoctorListActivity extends AppCompatActivity {

    private DoctorListViewModel doctorListViewModel;

    FrameLayout viewDoctorLayout;
    ConstraintLayout doctorListLayout;

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        viewDoctorLayout = findViewById(R.id.view_doc_frame);
        viewDoctorLayout.setVisibility(View.GONE);

        doctorListLayout = findViewById(R.id.doctor_list_Layout);
        doctorListLayout.setVisibility(View.VISIBLE);

        title = findViewById(R.id.doc_list_title);

        RecyclerView recyclerView = findViewById(R.id.doc_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String specialization = getIntent().getStringExtra(INTENT_EXTRA_SPECIALIZATION);

        title.setText(specialization);

        DoctorListAdapter adapter = new DoctorListAdapter(this);
        recyclerView.setAdapter(adapter);

        doctorListViewModel = ViewModelProviders.of(this).get(DoctorListViewModel.class);
        doctorListViewModel.getData(specialization).observe(this, doctors -> adapter.setDoctors(doctors));
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            viewDoctorLayout.setVisibility(View.VISIBLE);
            doctorListLayout.setVisibility(View.GONE);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.view_doc_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (viewDoctorLayout.getVisibility() == View.GONE) {
            super.onBackPressed();
        }
        viewDoctorLayout.setVisibility(View.GONE);
        doctorListLayout.setVisibility(View.VISIBLE);

    }
}
