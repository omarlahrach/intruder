package com.ailyan.intrus.ui.views.dialogs;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.ui.viewModels.ProgressViewModel;
import com.ailyan.intrus.ui.views.fragments.LevelFragment;
import com.ailyan.intrus.ui.views.fragments.MainFragment;

import java.util.Objects;

public class ScoreDialogFragment extends DialogFragment {
    private View root;
    private TextView textView_title;
    private TextView textView_rate;
    private ImageView ic_star1;
    private ImageView ic_star2;
    private ImageView ic_star3;
    private ImageView button_retrying;
    private ImageView button_home;

    private final LevelEntity level;

    public ScoreDialogFragment(LevelEntity level) {
        this.level = level;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialog_score, container,false);
        loadUIElements();
        showData();
        handleClicks();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            Objects.requireNonNull(getDialog()).getWindow().setLayout((int) (width - width * 0.1), (int) (height - height * 0.2));
        else
            Objects.requireNonNull(getDialog()).getWindow().setLayout((int) (width - width * 0.1), (int) (height - height * 0.6));
    }

    private void handleClicks() {
        setCancelable(false);
        ProgressViewModel progressViewModel = new ViewModelProvider(requireActivity()).get(ProgressViewModel.class);
        button_retrying.setOnClickListener(view ->  {
            progressViewModel.getPoints().postValue(0);
            this.dismiss();
            Bundle args = new Bundle();
            args.putSerializable("selectedLevel", level);
            FragmentManager fragmentManager =  requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, MainFragment.class, args, "Category")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        });
        button_home.setOnClickListener(view -> {
            progressViewModel.getPoints().postValue(0);
            this.dismiss();
            FragmentManager fragmentManager =  requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, LevelFragment.class, null, "Level")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        });
    }

    private void showData() {
        String title = "Niveau " + level.title;
        textView_title.setText(title);
        String rate = level.score.getRate() + "%";
        textView_rate.setText(rate);
        switch (level.score.getStarsNumber()) {
            case 1:
                ic_star1.setColorFilter(getResources().getColor(R.color.amber, null));
                break;
            case 2:
                ic_star1.setColorFilter(getResources().getColor(R.color.amber, null));
                ic_star2.setColorFilter(getResources().getColor(R.color.amber, null));
                break;
            case 3:
                ic_star1.setColorFilter(getResources().getColor(R.color.amber, null));
                ic_star2.setColorFilter(getResources().getColor(R.color.amber, null));
                ic_star3.setColorFilter(getResources().getColor(R.color.amber, null));
        }

    }

    private void loadUIElements() {
        textView_title = root.findViewById(R.id.textView_title);
        textView_rate = root.findViewById(R.id.textView_rate);
        button_retrying = root.findViewById(R.id.button_retrying);
        button_home = root.findViewById(R.id.button_home);
        ic_star1 = root.findViewById(R.id.ic_star1);
        ic_star2 = root.findViewById(R.id.ic_star2);
        ic_star3 = root.findViewById(R.id.ic_star3);
    }
}








