package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.ui.viewModels.AnswerViewModel;
import com.ailyan.intrus.ui.viewModels.GameViewModel;
import com.ailyan.intrus.utilities.ConnectionLiveData;

public class AnswerFragment extends Fragment {
    private static final String TAG = AnswerFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getContext());
        GameViewModel gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        AnswerViewModel answerViewModel = new ViewModelProvider(requireActivity()).get(AnswerViewModel.class);

        connectionLiveData.observe(getViewLifecycleOwner(), isConnected ->
                gameViewModel.currentQuestion().observe(getViewLifecycleOwner(), currentQuestion -> {
                    if (isConnected)
                        answerViewModel.loadRemoteAnswersByQuestion(currentQuestion).observe(getViewLifecycleOwner(), remoteAnswers -> {
                            for (AnswerEntity answerEntity : remoteAnswers)
                                Log.d(TAG, answerEntity.toString());
                        });
                    else
                        answerViewModel.loadLocalAnswersByQuestion(currentQuestion).observe(getViewLifecycleOwner(), localAnswers -> {
                            if (localAnswers != null)
                                Log.d(TAG, localAnswers.isEmpty() + " ");
                            else
                                Toast.makeText(getContext(), R.string.offline_message, Toast.LENGTH_LONG).show();
                        });
                }));
    }
}