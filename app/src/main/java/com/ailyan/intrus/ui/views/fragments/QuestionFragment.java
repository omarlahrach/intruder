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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;
import com.ailyan.intrus.ui.viewModels.GameViewModel;
import com.ailyan.intrus.ui.viewModels.QuestionViewModel;
import com.ailyan.intrus.utilities.ConnectionLiveData;

public class QuestionFragment extends Fragment {
    private static final String TAG = QuestionFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getContext());
        GameViewModel gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        QuestionViewModel questionViewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);

        connectionLiveData.observe(getViewLifecycleOwner(), isConnected ->
                gameViewModel.selectedLevel().observe(getViewLifecycleOwner(), selectedLevel -> {
                    if (isConnected)
                        questionViewModel.loadRemoteQuestionsByLevel(selectedLevel).observe(getViewLifecycleOwner(), remoteQuestions ->
                                gameViewModel.currentQuestion().postValue(remoteQuestions.get(0)));
                    else
                        questionViewModel.loadLocalQuestionsByLevel(selectedLevel).observe(getViewLifecycleOwner(), localQuestions -> {
                            if (localQuestions != null)
                                gameViewModel.currentQuestion().postValue(localQuestions.get(0));
                            else
                                Toast.makeText(getContext(), R.string.offline_message, Toast.LENGTH_LONG).show();
                        });
                }));
    }
}