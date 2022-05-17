package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;
import com.ailyan.intrus.ui.viewModels.AnswerViewModel;
import com.ailyan.intrus.ui.viewModels.ProgressViewModel;
import com.ailyan.intrus.ui.viewModels.QuestionViewModel;
import com.ailyan.intrus.ui.views.dialogs.ResultDialogFragment;
import com.ailyan.intrus.utilities.enums.AnswerState;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    //private static final String TAG = MainFragment.class.getSimpleName();
    private int selectedLevel;
    private List<QuestionEntity> questions;
    private int currentQuestionIndex;
    private final List<AnswerEntity> selectedAnswers = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null)
            selectedLevel = arguments.getInt("selectedLevel");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button_next = view.findViewById(R.id.button_next);

        QuestionViewModel questionViewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);
        AnswerViewModel answerViewModel = new ViewModelProvider(requireActivity()).get(AnswerViewModel.class);
        ProgressViewModel progressViewModel = new ViewModelProvider(requireActivity()).get(ProgressViewModel.class);

        questionViewModel.loadLocalQuestionsByLevel(selectedLevel).observe(getViewLifecycleOwner(), questions -> {
            if (!questions.isEmpty()) {
                this.questions = questions;
                this.currentQuestionIndex = 1;
                progressViewModel.getQuestionIndex().postValue(currentQuestionIndex);
                progressViewModel.getQuestionsCount().postValue(questions.size());
                questionViewModel.selectedQuestion().postValue(questions.get(currentQuestionIndex - 1));
                progressViewModel.getPoints().postValue(0);
            }
        });

        answerViewModel.selectedAnswer().observe(getViewLifecycleOwner(), answer -> {
            if (selectedAnswers.contains(answer))
                selectedAnswers.remove(answer);
            else
                selectedAnswers.add(answer);

            if (selectedAnswers.isEmpty())
                button_next.setVisibility(View.INVISIBLE);
            else
                button_next.setVisibility(View.VISIBLE);
        });

        button_next.setOnClickListener(btn -> {
            AnswerState answerState = AnswerState.CORRECT;
            for (AnswerEntity selectedAnswer : selectedAnswers)
                if (!selectedAnswer.isCorrect) {
                    answerState = AnswerState.INCORRECT;
                    break;
                }
            new ResultDialogFragment(answerState).show(getChildFragmentManager(), "Result");
            boolean gameFinished = currentQuestionIndex == questions.size();
            if (!gameFinished) {
                int nextQuestionIndex = ++currentQuestionIndex;
                questionViewModel.selectedQuestion().postValue(questions.get(nextQuestionIndex - 1));
                progressViewModel.getQuestionIndex().postValue(nextQuestionIndex);
            }
            button_next.setVisibility(View.INVISIBLE);
            selectedAnswers.clear();
        });
    }
}