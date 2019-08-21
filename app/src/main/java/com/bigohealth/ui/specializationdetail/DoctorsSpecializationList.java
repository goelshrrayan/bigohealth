package com.bigohealth.ui.specializationdetail;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigohealth.R;
import com.bigohealth.model.Doctor;
import com.bumptech.glide.Glide;

import java.util.List;

public class DoctorsSpecializationList extends RecyclerView.Adapter<DoctorsSpecializationList.ViewHolder> {

    private List<Doctor> doctorList;
    private Context context;

    public DoctorsSpecializationList(List<Doctor> doctorList, Context context) {
        this.doctorList = doctorList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_lookup, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor data = doctorList.get(position);
        Glide.with(context).load(data.getDocImgUrl()).into(holder.iv_doctors);
        holder.tv_docName.setText(data.getDocFirstname() + " " + data.getDocLastname());
        holder.tv_docQualification.setText(data.getQualification());
        holder.tv_docPrice.setText(data.getDocFee() + "");
        if (data.getDocDiscountedFee() != null) {
            holder.tv_docPriceDiscount.setText(data.getDocDiscountedFee());
            holder.tv_docPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tv_docPriceDiscount.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_doctors;
        TextView tv_docName;
        TextView tv_docQualification;
        TextView tv_docPrice;
        TextView tv_docPriceDiscount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_doctors = itemView.findViewById(R.id.iv_doctor);
            tv_docName = itemView.findViewById(R.id.tv_doctor_name);
            tv_docQualification = itemView.findViewById(R.id.tv_doctor_qualification);
            tv_docPrice = itemView.findViewById(R.id.tv_doctor_price);
            tv_docPriceDiscount = itemView.findViewById(R.id.tv_doctor_price_discounted);
        }
    }
}
