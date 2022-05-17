package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.ui.viewModels.AnswerViewModel;
import com.ailyan.intrus.ui.viewModels.ProgressViewModel;
import com.ailyan.intrus.ui.viewModels.QuestionViewModel;
import com.ailyan.intrus.utilities.Animation;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {
    //private static final String TAG = QuestionFragment.class.getSimpleName();
    private TextView textView_questionStatement;
    private LinearLayout layout_attempts;
    private TextView textView_attempts;
    private ImageView imageView_heart1;
    private ImageView imageView_heart2;
    private ImageView imageView_heart3;
    private int attempts;
    private int points;
    private final List<AnswerEntity> selectedAnswers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView_questionStatement = view.findViewById(R.id.textView_questionStatement);
        layout_attempts = view.findViewById(R.id.layout_attempts);
        textView_attempts = view.findViewById(R.id.textView_attempts);
        imageView_heart1 = view.findViewById(R.id.imageView_heart1);
        imageView_heart2 = view.findViewById(R.id.imageView_heart2);
        imageView_heart3 = view.findViewById(R.id.imageView_heart3);

        AnswerViewModel answerViewModel = new ViewModelProvider(requireActivity()).get(AnswerViewModel.class);
        QuestionViewModel questionViewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);
        ProgressViewModel progressViewModel = new ViewModelProvider(requireActivity()).get(ProgressViewModel.class);

        questionViewModel.selectedQuestion().observe(getViewLifecycleOwner(), question -> {
            attempts = 3;
            textView_attempts.setText(R.string.three_more_attempts);
            imageView_heart1.setVisibility(View.VISIBLE);
            imageView_heart2.setVisibility(View.VISIBLE);
            imageView_heart3.setVisibility(View.VISIBLE);
            textView_questionStatement.setText(question.statement);
            Animation.appear(textView_questionStatement);
            Animation.appear(layout_attempts);
        });

        answerViewModel.selectedAnswer().observe(getViewLifecycleOwner(), answer -> {
            if (selectedAnswers.contains(answer)) {
                selectedAnswers.remove(answer);
                if (answer.isCorrect)
                    points -= 10;
            } else {
                selectedAnswers.add(answer);
                if (answer.isCorrect)
                    points += 10;
                else {
                    if (attempts != 0)
                        attempts--;
                    else
                        points -= 10;
                }
            }

            progressViewModel.getPoints().postValue(points);

            switch (attempts) {
                case 2:
                    textView_attempts.setText(R.string.two_more_attempts);
                    imageView_heart1.setVisibility(View.GONE);
                    break;
                case 1:
                    textView_attempts.setText(R.string.one_more_attempt);
                    imageView_heart2.setVisibility(View.GONE);
                    break;
                case 0:
                    textView_attempts.setText(R.string.no_more_attempts);
                    imageView_heart3.setVisibility(View.GONE);
                    break;
            }
        });
    }
}