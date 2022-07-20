package com.ailyan.intrus.ui.views.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.ui.viewModels.ProgressViewModel;
import com.ailyan.intrus.ui.views.dialogs.QuitDialogFragment;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class ProgressFragment extends Fragment {
    private CircularProgressIndicator indicator_points;
    private TextView textView_points_inner;
    private TextView textView_points_outer;
    private LinearProgressIndicator indicator_questionProgress;
    private TextView textView_questionProgress;
    private int questionsCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        indicator_points = view.findViewById(R.id.indicator_points);
        textView_points_inner = view.findViewById(R.id.textView_points_inner);
        textView_points_outer = view.findViewById(R.id.textView_points_outer);
        indicator_questionProgress = view.findViewById(R.id.indicator_questionProgress);
        textView_questionProgress = view.findViewById(R.id.textView_questionProgress);

        ProgressViewModel progressViewModel = new ViewModelProvider(requireActivity()).get(ProgressViewModel.class);

        int orientation = getResources().getConfiguration().orientation;
        progressViewModel.getQuestionsCount().observe(getViewLifecycleOwner(), questionsCount -> {
            this.questionsCount = questionsCount;
            indicator_points.setMax(questionsCount * 10);
            indicator_points.setProgress(0);
            if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                textView_points_inner.setText(getResources().getString(
                        R.string.points_progress_text_landscape, 0
                ));
            else
                textView_points_outer.setText(getResources().getString(
                        R.string.points_progress_text_portrait, 0, questionsCount * 10
                ));
            indicator_questionProgress.setMax(questionsCount);
            progressViewModel.getQuestionIndex().observe(getViewLifecycleOwner(), questionIndex -> {
                textView_questionProgress.setText(getResources().getString(
                        R.string.question_progress_text, questionIndex, questionsCount));
                indicator_questionProgress.setProgress(questionIndex, true);
            });
        });

        progressViewModel.getPoints().observe(getViewLifecycleOwner(), points -> {
            indicator_points.setProgress(points, true);
            if (orientation == Configuration.ORIENTATION_LANDSCAPE)
                textView_points_inner.setText(getResources().getString(
                        R.string.points_progress_text_landscape, points
                ));
            else
                textView_points_outer.setText(getResources().getString(
                        R.string.points_progress_text_portrait, points, questionsCount * 10
                ));
        });

        ImageButton button_home = view.findViewById(R.id.button_home);
        button_home.setOnClickListener(btn -> new QuitDialogFragment().show(getChildFragmentManager(), "Quit main"));
    }
}