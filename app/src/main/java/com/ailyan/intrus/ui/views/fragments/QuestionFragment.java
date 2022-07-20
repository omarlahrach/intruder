package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.ui.viewModels.QuestionViewModel;
import com.ailyan.intrus.utilities.Animation;

public class QuestionFragment extends Fragment {
    //private static final String TAG = QuestionFragment.class.getSimpleName();
    private TextView textView_questionStatement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView_questionStatement = view.findViewById(R.id.textView_questionStatement);

        QuestionViewModel questionViewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);

        questionViewModel.selectedQuestion().observe(getViewLifecycleOwner(), question -> {
            textView_questionStatement.setText(question.statement);
            Animation.appear(textView_questionStatement);
        });
    }
}