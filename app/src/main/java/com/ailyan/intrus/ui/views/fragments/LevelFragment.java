package com.ailyan.intrus.ui.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.ui.viewModels.LevelViewModel;
import com.ailyan.intrus.ui.views.activities.GameActivity;
import com.ailyan.intrus.ui.views.adapters.LevelAdapter;
import com.ailyan.intrus.utilities.ConnectionLiveData;

import java.util.List;

public class LevelFragment extends Fragment implements LevelAdapter.ItemClickListener {
    private static final String TAG = LevelFragment.class.getSimpleName();
    private View root;
    private RecyclerView recyclerView_levels;
    private List<LevelEntity> levels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_level, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView_levels = root.findViewById(R.id.recyclerView_levels);

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getContext());
        LevelViewModel levelViewModel = new ViewModelProvider(requireActivity()).get(LevelViewModel.class);

        connectionLiveData.observe(getViewLifecycleOwner(), isConnected -> {
            if (isConnected)
                levelViewModel.loadAllRemoteLevels().observe(getViewLifecycleOwner(), remoteLevels -> {
                    this.levels = remoteLevels;
                    showLevels();
                });
            else
                levelViewModel.loadAllLocalLevels().observe(getViewLifecycleOwner(), localLevels -> {
                    if (localLevels != null) {
                        this.levels = localLevels;
                        showLevels();
                    } else
                        Toast.makeText(getContext(), R.string.offline_message, Toast.LENGTH_LONG).show();
                });
        });
    }

    private void showLevels() {
        LevelAdapter levelAdapter = new LevelAdapter(getContext(), levels);
        levelAdapter.setClickListener(this);
        recyclerView_levels.setAdapter(levelAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView_levels.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(requireActivity(), GameActivity.class);
        intent.putExtra("selectedLevel", levels.get(position));
        startActivity(intent);
    }
}