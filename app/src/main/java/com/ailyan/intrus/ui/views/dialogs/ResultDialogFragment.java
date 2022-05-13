package com.ailyan.intrus.ui.views.dialogs;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ailyan.intrus.R;
import com.ailyan.intrus.utilities.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultDialogFragment extends DialogFragment {
    private View root;
    private TextView textView_title;
    private ImageView imageView_emoji;
    private TextView textView_answer;
    private Button btn_ok;

    private final boolean congrats;

    public ResultDialogFragment(boolean congrats) {
        this.congrats = congrats;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialog_result, container,false);
        loadUIElements();
        prepareUi();
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
        setCancelable(false);
        btn_ok.setOnClickListener(view -> dismiss());
    }

    private void prepareUi() {
        List<Integer> win_emojis = new ArrayList<>();
        List<Integer> lose_emojis = new ArrayList<>();

        win_emojis.add(R.drawable.ic_star);
        win_emojis.add(R.drawable.ic_smiling);
        win_emojis.add(R.drawable.ic_cool);
        win_emojis.add(R.drawable.ic_happy);
        win_emojis.add(R.drawable.ic_party);

        lose_emojis.add(R.drawable.ic_shocked);
        lose_emojis.add(R.drawable.ic_angry);
        lose_emojis.add(R.drawable.ic_sad);
        lose_emojis.add(R.drawable.ic_thinking);
        lose_emojis.add(R.drawable.ic_laughing);

        int win_emoji = win_emojis.get(Helper.getRandomNumber(win_emojis.size()));
        int lose_emoji = lose_emojis.get(Helper.getRandomNumber(lose_emojis.size()));

        int emoji = congrats ? win_emoji : lose_emoji;

        String title = congrats ? "Correcte" : "Incorrect";
        String message = congrats ? "Félicitation" : "Prochaine fois";

        textView_title.setText(title);
        imageView_emoji.setImageResource(emoji);
        textView_answer.setText(message);
    }

    private void loadUIElements() {
        textView_title = root.findViewById(R.id.textView_title);
        imageView_emoji = root.findViewById(R.id.imageView_emoji);
        textView_answer = root.findViewById(R.id.textView_answer);
        btn_ok = root.findViewById(R.id.btn_ok);
    }
}








