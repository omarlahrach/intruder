package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.ui.viewModels.AnswerViewModel;
import com.ailyan.intrus.ui.viewModels.LoginViewModel;
import com.ailyan.intrus.ui.viewModels.QuestionViewModel;
import com.ailyan.intrus.ui.views.adapters.AnswerAdapter;

import java.util.Collections;
import java.util.List;

public class AnswerFragment extends Fragment implements AnswerAdapter.ItemClickListener {
    //private static final String TAG = AnswerFragment.class.getSimpleName();
    private RecyclerView recyclerView_answers;
    private LinearLayout layout_loading;
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
        layout_loading = view.findViewById(R.id.layout_loading);

        LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        answerViewModel = new ViewModelProvider(requireActivity()).get(AnswerViewModel.class);
        QuestionViewModel questionViewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);

        loginViewModel.selectedDataSource().observe(getViewLifecycleOwner(), dataSource ->
                questionViewModel.selectedQuestion().observe(getViewLifecycleOwner(), question -> {
                    recyclerView_answers.setVisibility(View.INVISIBLE);
                    layout_loading.setVisibility(View.VISIBLE);
                    answerViewModel.loadAnswersByQuestionId(loginViewModel, dataSource, question.id).observe(getViewLifecycleOwner(), answers -> {
                        boolean sameAnswers = true;
                        if (this.answers == null)
                            sameAnswers = false;
                        else {
                            if (this.answers.size() != answers.size())
                                sameAnswers = false;
                            else {
                                for (int i = 0; i < answers.size(); i++)
                                    if (!answers.get(i).imageUrl.equals(this.answers.get(i).imageUrl)) {
                                        sameAnswers = false;
                                        break;
                                    }
                            }
                        }
                        if (!sameAnswers) {
                            this.answers = answers;
                            Collections.shuffle(answers);
                            showAnswers();
                        }
                    });
                })
        );
    }

    private void showAnswers() {
        AnswerAdapter answerAdapter = new AnswerAdapter(getContext(), answers, answerViewModel);
        answerAdapter.setClickListener(this);
        recyclerView_answers.setAdapter(answerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView_answers.setLayoutManager(gridLayoutManager);
        recyclerView_answers.setVisibility(View.VISIBLE);
        layout_loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}