package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;
import com.ailyan.intrus.databinding.FragmentMainBinding;
import com.ailyan.intrus.ui.viewModels.AnswerViewModel;
import com.ailyan.intrus.ui.viewModels.LevelViewModel;
import com.ailyan.intrus.ui.viewModels.ProgressViewModel;
import com.ailyan.intrus.ui.viewModels.QuestionViewModel;
import com.ailyan.intrus.ui.views.dialogs.ResultDialogFragment;
import com.ailyan.intrus.ui.views.dialogs.ScoreDialogFragment;
import com.ailyan.intrus.utilities.enums.AnswerState;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private LevelEntity selectedLevel;
    private List<QuestionEntity> questions;
    private int currentQuestionIndex = 1;
    private List<AnswerEntity> selectedAnswers = new ArrayList<>();
    private int questionsCount;
    private boolean incrementPoints = true;
    private int points;
    private FragmentMainBinding binding;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("questions", (ArrayList<? extends Parcelable>) questions);
        outState.putInt("currentQuestionIndex", currentQuestionIndex);
        outState.putInt("points", points);
        outState.putParcelableArrayList("selectedAnswers", (ArrayList<? extends Parcelable>) selectedAnswers);
        binding.buttonNext.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            questions = savedInstanceState.getParcelableArrayList("questions");
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex");
            points = savedInstanceState.getInt("points");
            selectedAnswers = savedInstanceState.getParcelableArrayList("selectedAnswers");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null)
            selectedLevel = (LevelEntity) arguments.getSerializable("selectedLevel");
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        QuestionViewModel questionViewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);
        LevelViewModel levelViewModel = new ViewModelProvider(requireActivity()).get(LevelViewModel.class);
        AnswerViewModel answerViewModel = new ViewModelProvider(requireActivity()).get(AnswerViewModel.class);
        ProgressViewModel progressViewModel = new ViewModelProvider(requireActivity()).get(ProgressViewModel.class);

        questionViewModel.loadLocalQuestionsByLevel(selectedLevel.id).observe(getViewLifecycleOwner(), questions -> {
            this.questions = questions;
            incrementPoints = true;
            questionsCount = this.questions.size();
            progressViewModel.getQuestionsCount().postValue(questionsCount);
            questionViewModel.selectedQuestion().postValue(this.questions.get(currentQuestionIndex - 1));
            progressViewModel.getQuestionIndex().postValue(currentQuestionIndex);
            progressViewModel.getPoints().postValue(points);
        });

        answerViewModel.selectedAnswer().observe(getViewLifecycleOwner(), answer -> {
            if (!answer.isCorrect)
                incrementPoints = false;
            if (selectedAnswers.contains(answer))
                selectedAnswers.remove(answer);
            else
                selectedAnswers.add(answer);

            if (selectedAnswers.isEmpty())
                binding.buttonNext.setVisibility(View.INVISIBLE);
            else
                binding.buttonNext.setVisibility(View.VISIBLE);
        });

        binding.buttonNext.setOnClickListener(btn -> {
            AnswerState answerState = AnswerState.CORRECT;
            for (AnswerEntity selectedAnswer : selectedAnswers)
                if (!selectedAnswer.isCorrect) {
                    answerState = AnswerState.INCORRECT;
                    break;
                }
            if (incrementPoints) {
                points += 10;
                progressViewModel.getPoints().postValue(points);
            }
            boolean gameFinished = currentQuestionIndex == questions.size();
            if (!gameFinished) {
                int nextQuestionIndex = ++currentQuestionIndex;
                questionViewModel.selectedQuestion().postValue(questions.get(nextQuestionIndex - 1));
                progressViewModel.getQuestionIndex().postValue(nextQuestionIndex);
                new ResultDialogFragment(answerState).show(getChildFragmentManager(), "Result");
            } else {
                boolean bestScore = points > selectedLevel.score.resultObtained;
                selectedLevel.score.resultObtained = points;
                selectedLevel.score.maximumResult = this.questionsCount * 10;
                new ScoreDialogFragment(selectedLevel).show(getChildFragmentManager(), "Score");
                if (bestScore)
                    levelViewModel.updateLevel(selectedLevel);
            }
            binding.buttonNext.setVisibility(View.INVISIBLE);
            selectedAnswers.clear();
            incrementPoints = true;
        });
    }
}