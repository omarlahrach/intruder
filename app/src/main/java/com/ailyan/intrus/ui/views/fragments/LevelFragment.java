package com.ailyan.intrus.ui.views.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.databinding.FragmentLevelBinding;
import com.ailyan.intrus.ui.viewModels.LevelViewModel;
import com.ailyan.intrus.ui.views.adapters.LevelAdapter;
import com.ailyan.intrus.utilities.SharedData;

import java.util.List;

public class LevelFragment extends Fragment implements LevelAdapter.ItemClickListener {
    private FragmentLevelBinding binding;
    private List<LevelEntity> levels;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLevelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LevelViewModel levelViewModel = new ViewModelProvider(requireActivity()).get(LevelViewModel.class);
        levelViewModel.loadAllLevels().observe(getViewLifecycleOwner(), levels -> {
            this.levels = levels;
            int rows, cols;
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rows = 2;
                cols = 2;
            } else {
                rows = 4;
                cols = 1;
            }
            LevelAdapter levelAdapter = new LevelAdapter(getContext(), levels, rows, binding);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), cols);
            levelAdapter.setClickListener(this);
            binding.levels.setAdapter(levelAdapter);
            binding.levels.setLayoutManager(gridLayoutManager);
        });

        int defaultTheme = AppCompatDelegate.getDefaultNightMode();
        boolean isSwitchChecked = defaultTheme == AppCompatDelegate.MODE_NIGHT_YES;
        binding.theme.setChecked(isSwitchChecked);
        binding.theme.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            int theme = isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
            AppCompatDelegate.setDefaultNightMode(theme);
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        LevelEntity selectedLevel = levels.get(position);
        Bundle args = new Bundle();
        args.putSerializable("selectedLevel", selectedLevel);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, MainFragment.class, args, "Main")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}