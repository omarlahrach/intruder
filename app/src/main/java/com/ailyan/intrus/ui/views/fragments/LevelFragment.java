package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ailyan.intrus.R;
import com.ailyan.intrus.ui.viewModels.LevelViewModel;
import com.ailyan.intrus.ui.views.adapters.LevelAdapter;

import java.util.List;

public class LevelFragment extends Fragment implements LevelAdapter.ItemClickListener {
    //private static final String TAG = LevelFragment.class.getSimpleName();
    private LevelViewModel levelViewModel;
    private RecyclerView recyclerView_levels;
    private List<Integer> levels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_level, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView_levels = view.findViewById(R.id.recyclerView_levels);
        levelViewModel = new ViewModelProvider(requireActivity()).get(LevelViewModel.class);
        showLevels();
    }

    private void showLevels() {
        levels = levelViewModel.loadAllLevels();
        LevelAdapter levelAdapter = new LevelAdapter(getContext(), levels);
        levelAdapter.setClickListener(this);
        recyclerView_levels.setAdapter(levelAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView_levels.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClick(View view, int position) {
        int selectedLevel = levels.get(position);
        Bundle args = new Bundle();
        args.putInt("selectedLevel", selectedLevel);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, MainFragment.class, args)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}