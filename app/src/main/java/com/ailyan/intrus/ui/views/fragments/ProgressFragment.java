package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.ui.viewModels.ProgressViewModel;
import com.ailyan.intrus.ui.views.dialogs.QuitDialogFragment;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public class ProgressFragment extends Fragment {
    private CircularProgressIndicator circularProgressIndicator_pointsProgress;
    private TextView textView_pointsProgress;
    private TextView textView_questionProgress;
    private int points = 0;
    private int questionIndex;
    private int questionsCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        circularProgressIndicator_pointsProgress = view.findViewById(R.id.circularProgressIndicator_pointsProgress);
        textView_pointsProgress = view.findViewById(R.id.textView_pointsProgress);
        textView_questionProgress = view.findViewById(R.id.textView_questionProgress);

        LinearLayout layout_points = view.findViewById(R.id.layout_points);
        circularProgressIndicator_pointsProgress.getLayoutParams().height = layout_points.getHeight();

        ProgressViewModel progressViewModel = new ViewModelProvider(requireActivity()).get(ProgressViewModel.class);

        progressViewModel.getPoints().observe(getViewLifecycleOwner(), points -> {
            this.points = points;
            circularProgressIndicator_pointsProgress.setProgress(points, true);
            textView_pointsProgress.setText(getResources().getString(R.string.points_progress_text, points, questionsCount * 10));
        });
        progressViewModel.getQuestionIndex().observe(getViewLifecycleOwner(), questionIndex -> {
            this.questionIndex = questionIndex;
            textView_questionProgress.setText(getResources().getString(R.string.question_progress_text, questionIndex, questionsCount));
        });
        progressViewModel.getQuestionsCount().observe(getViewLifecycleOwner(), questionsCount -> {
            this.questionsCount = questionsCount;
            circularProgressIndicator_pointsProgress.setMax(questionsCount * 10);
            textView_questionProgress.setText(getResources().getString(R.string.question_progress_text, questionIndex, questionsCount));
            textView_pointsProgress.setText(getResources().getString(R.string.points_progress_text, points, questionsCount * 10));
        });

        ImageButton button_home = view.findViewById(R.id.button_home);
        button_home.setOnClickListener(btn -> new QuitDialogFragment().show(getChildFragmentManager(), "Quit level"));
    }
}