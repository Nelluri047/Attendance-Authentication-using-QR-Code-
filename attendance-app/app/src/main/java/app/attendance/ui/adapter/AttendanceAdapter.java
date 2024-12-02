package app.attendance.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import app.attendance.R;
import app.attendance.data.models.Attendance;
import app.attendance.data.models.StudentCourse;
import app.attendance.databinding.ItemAttendanceBinding;
import kotlin.collections.ArrayDeque;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    private final List<Attendance> list;

    public AttendanceAdapter() {
        this.list = new LinkedList<>();
    }

    public void addAll(List<Attendance> list) {
        for(Attendance attendance : list) {
            this.list.add(attendance);
            notifyItemInserted(getItemCount() - 1);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AttendanceViewHolder(
                ItemAttendanceBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AttendanceViewHolder extends RecyclerView.ViewHolder {

        private final ItemAttendanceBinding binding;

        public AttendanceViewHolder(ItemAttendanceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Attendance attendance) {
            binding.date.setText(attendance.getDate());
            binding.time.setText(attendance.getTime());
            binding.status.setText(attendance.getStatus());
        }
    }
}
