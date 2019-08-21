package com.bigohealth.ui.homeactivity;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigohealth.R;

import java.util.ArrayList;

public class SpecializationAdapter extends RecyclerView.Adapter<SpecializationAdapter.ViewHolder> {

    private ArrayList<String> specialization;
    private OnItemClick listener;

    public SpecializationAdapter(ArrayList<String> specialization, OnItemClick listener) {
        this.specialization = specialization;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctors, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_specialization.setText(specialization.get(position));
        holder.itemView.setOnClickListener(view -> listener.onClick(specialization.get(position)));
    }

    @Override
    public int getItemCount() {
        return specialization.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_specialization;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_specialization = itemView.findViewById(R.id.tv_doctors);
        }
    }

    public interface OnItemClick {
        void onClick(String specialization);
    }

    public static class ItemSpace extends RecyclerView.ItemDecoration {

        private final int space;

        ItemSpace(int space) {
            super();
            this.space = space;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            outRect.right = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = space;
            }
        }
    }
}
