package com.ailyan.intrus.ui.views.dialogs;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ailyan.intrus.R;
import com.ailyan.intrus.ui.views.fragments.LevelFragment;
import com.ailyan.intrus.ui.views.fragments.MainFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class QuitDialogFragment extends DialogFragment {
    private View root;
    private Button btn_cancel;
    private Button btn_yes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialog_quit, container, false);
        loadUIElements();
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
        Objects.requireNonNull(getDialog()).getWindow().setLayout((int) (width - width * 0.2), (int) (height - height * 0.2));
    }

    private void handleClicks() {
        btn_cancel.setOnClickListener(view -> this.dismiss());
        btn_yes.setOnClickListener(view -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, LevelFragment.class, null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        });
    }

    private void loadUIElements() {
        btn_cancel = root.findViewById(R.id.btn_cancel);
        btn_yes = root.findViewById(R.id.btn_yes);
        btn_cancel.setTextSize(btn_yes.getTextSize());
    }
}
