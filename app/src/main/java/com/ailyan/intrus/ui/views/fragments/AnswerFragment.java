package com.ailyan.intrus.ui.views.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.databinding.FragmentAnswerBinding;
import com.ailyan.intrus.ui.viewModels.AnswerViewModel;
import com.ailyan.intrus.ui.viewModels.LoginViewModel;
import com.ailyan.intrus.ui.viewModels.QuestionViewModel;
import com.ailyan.intrus.ui.views.adapters.AnswerAdapter;
import com.ailyan.intrus.utilities.enums.DataSource;

import java.util.Collections;
import java.util.List;

public class AnswerFragment extends Fragment implements AnswerAdapter.ItemClickListener {
    private FragmentAnswerBinding binding;
    private AnswerViewModel answerViewModel;
    private List<AnswerEntity> answers;
    private int selectedQuestionId;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedQuestionId", selectedQuestionId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            selectedQuestionId = savedInstanceState.getInt("selectedQuestionId");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAnswerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        answerViewModel = new ViewModelProvider(requireActivity()).get(AnswerViewModel.class);
        QuestionViewModel questionViewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);

        loginViewModel.selectedDataSource().observe(getViewLifecycleOwner(), dataSource ->
                questionViewModel.selectedQuestion().observe(getViewLifecycleOwner(), question -> {
                    binding.answers.setVisibility(View.INVISIBLE);
                    binding.loading.setVisibility(View.VISIBLE);
                    if (question.id != selectedQuestionId) {
                        selectedQuestionId = question.id;
                        answerViewModel.loadAnswersByQuestionId(loginViewModel, dataSource, selectedQuestionId)
                                .observe(getViewLifecycleOwner(), answers -> {
                                    if (this.answers != answers) {
                                        this.answers = answers;
                                        showAnswers();
                                    }
                                });
                    } else
                        answerViewModel.loadAnswersByQuestionId(loginViewModel, DataSource.LOCAL, selectedQuestionId)
                                .observe(getViewLifecycleOwner(), answers -> {
                                    if (this.answers != answers) {
                                        this.answers = answers;
                                        showAnswers();
                                    }
                                });
                })
        );
    }

    private void showAnswers() {
        int rows, cols;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rows = 2;
            cols = 4;
        } else {
            rows = 3;
            cols = 3;
        }
        Collections.shuffle(answers);
        AnswerAdapter answerAdapter = new AnswerAdapter(getContext(), answers, answerViewModel, rows, binding);
        binding.answers.setAdapter(answerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), cols);
        binding.answers.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}