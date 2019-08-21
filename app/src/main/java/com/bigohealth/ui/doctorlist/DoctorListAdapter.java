package com.bigohealth.ui.doctorlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bigohealth.R;
import com.bigohealth.model.Doctor;
import com.bigohealth.viewmodels.DoctorListViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

    private Context context;
    DoctorListViewModel viewModel;
    List<Doctor> doctors = new ArrayList<>();

    DoctorListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.doctor_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Doctor currentDoc = doctors.get(position);
        viewHolder.name.setText(currentDoc.getDocFirstname() + " " + currentDoc.getDocLastname());
        viewHolder.specialisation.setText(currentDoc.getSpecialisation());
        viewHolder.qualification.setText(currentDoc.getQualification());
        Picasso.get().load(currentDoc.getDocImgUrl()).into(viewHolder.doc_image);

        viewHolder.doc_fee.setText("Rs." + currentDoc.getDocFee());
        viewHolder.availability.setText(currentDoc.getGeneralSlot());

        viewHolder.cardView.setOnClickListener(v -> {
            ViewDoctorDetails fragment = new ViewDoctorDetails(currentDoc);
            DoctorListActivity activity = (DoctorListActivity) context;
            activity.loadFragment(fragment);

        });

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
        notifyDataSetChanged(); //TODO change this, for debugging purpose only
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, qualification, specialisation, availability, doc_fee;
        ImageView doc_image;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            qualification = itemView.findViewById(R.id.doc_quali);
            specialisation = itemView.findViewById(R.id.doc_speci);
            availability = itemView.findViewById(R.id.doc_availability);
            doc_fee = itemView.findViewById(R.id.doc_price);
            name = itemView.findViewById(R.id.doc_name);
            cardView = itemView.findViewById(R.id.card);
            doc_image = itemView.findViewById(R.id.dl_doc_image);
        }
    }
}
