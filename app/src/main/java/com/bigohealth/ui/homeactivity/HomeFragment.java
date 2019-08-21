package com.bigohealth.ui.homeactivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigohealth.R;
import com.bigohealth.ui.doctorlist.DoctorListActivity;
import com.bigohealth.viewmodels.HomeFragViewModel;

import static com.bigohealth.utils.Constant.INTENT_EXTRA_SPECIALIZATION;
import static com.bigohealth.utils.Constant.specializations;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private HomeFragViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(HomeFragViewModel.class);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView doctorsList = view.findViewById(R.id.rv_doctors);
        doctorsList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        doctorsList.addItemDecoration(new SpecializationAdapter.ItemSpace(36));
        doctorsList.setAdapter(new SpecializationAdapter(specializations, specialization -> {
            Intent intent = new Intent(getContext(), DoctorListActivity.class);
            intent.putExtra(INTENT_EXTRA_SPECIALIZATION, specialization);
            startActivity(intent);
        }));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getData();
    }
}
