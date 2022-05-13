package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.ui.viewModels.AnswerViewModel;
import com.ailyan.intrus.ui.viewModels.QuestionViewModel;
import com.ailyan.intrus.ui.views.adapters.AnswerAdapter;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.Collections;
import java.util.List;

public class AnswerFragment extends Fragment implements AnswerAdapter.ItemClickListener {
    //private static final String TAG = AnswerFragment.class.getSimpleName();
    private RecyclerView recyclerView_answers;
    private CircularProgressIndicator circularProgressIndicator_loading;
    private List<AnswerEntity> answers;
    private AnswerViewModel answerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView_answers = view.findViewById(R.id.recyclerView_answers);
        circularProgressIndicator_loading = view.findViewById(R.id.circularProgressIndicator_loading);

        answerViewModel = new ViewModelProvider(requireActivity()).get(AnswerViewModel.class);
        QuestionViewModel questionViewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);

        questionViewModel.selectedQuestion().observe(getViewLifecycleOwner(), question ->
                answerViewModel.loadLocalAnswersByQuestionId(question.id).observe(getViewLifecycleOwner(), answers -> {
                    this.answers = answers;
                    if (answers.isEmpty())
                        circularProgressIndicator_loading.setVisibility(View.VISIBLE);
                    else
                        circularProgressIndicator_loading.setVisibility(View.INVISIBLE);
                    Collections.shuffle(answers);
                    showAnswers();
                })
        );
    }

    private void showAnswers() {
        AnswerAdapter answerAdapter = new AnswerAdapter(getContext(), answers, answerViewModel);
        answerAdapter.setClickListener(this);
        recyclerView_answers.setAdapter(answerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView_answers.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}