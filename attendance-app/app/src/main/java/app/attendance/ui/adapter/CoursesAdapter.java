package app.attendance.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.attendance.R;
import app.attendance.data.models.Course;
import app.attendance.data.models.StudentCourse;
import app.attendance.databinding.ItemCourseBinding;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseViewHolder> {

    private final List<StudentCourse> list;
    private final OnItemClickListener itemClickListener;

    public CoursesAdapter(OnItemClickListener listener) {
        list = new ArrayList<>();

        itemClickListener = listener;
    }

    public void addAll(List<StudentCourse> list) {
        for(StudentCourse course : list) {
            this.list.add(course);
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
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseViewHolder(
                ItemCourseBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.bind(list.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final ItemCourseBinding binding;

        public CourseViewHolder(ItemCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StudentCourse studentCourse, OnItemClickListener listener) {
            Context context = itemView.getContext();
            Course course = studentCourse.getCourse();
            String courseName = context.getString(R.string.course_name,
                    course.getSubject().getCode(),
                    course.getSubject().getName());
            binding.subjectName.setText(courseName);
            binding.semester.setText(course.getSemester());

            String instructor = context.getString(R.string.instructor, course.getInstructorName());
            binding.instructor.setText(instructor);

            String classTiming = context.getString(R.string.class_timing,
                    course.getClassStartTime(),
                    course.getClassEndTime());
            binding.classTiming.setText(classTiming);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(studentCourse);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(StudentCourse studentCourse);
    }
}
