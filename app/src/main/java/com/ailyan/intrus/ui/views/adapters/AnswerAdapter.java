package com.ailyan.intrus.ui.views.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.ui.viewModels.AnswerViewModel;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private final Context context;
    private final List<AnswerEntity> answers;
    private final AnswerViewModel answerViewModel;
    private final LayoutInflater mInflater;
    private AnswerAdapter.ItemClickListener mClickListener;

    public AnswerAdapter(Context context, List<AnswerEntity> answers, AnswerViewModel answerViewModel) {
        this.mInflater = LayoutInflater.from(context);
        this.answers = answers;
        this.context = context;
        this.answerViewModel = answerViewModel;
    }

    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_answer, parent, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        ViewGroup.MarginLayoutParams lpv = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        lp.height = parent.getMeasuredHeight() / 2 - lpv.topMargin * 2;
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {
        AnswerEntity answer = answers.get(position);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(answer.image, 0, answer.image.length);
        if (answer.imageUrl != null)
            Picasso.get().load(answer.imageUrl).into(holder.imageView_answer);
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView cardView_answer;
        ImageView imageView_answer;

        ViewHolder(View itemView) {
            super(itemView);
            cardView_answer = itemView.findViewById(R.id.cardView_answer);
            imageView_answer = itemView.findViewById(R.id.imageView_answer);
            itemView.setOnClickListener(this);
            cardView_answer.setOnClickListener(view -> {
                answerViewModel.selectedAnswer().postValue(answers.get(getAdapterPosition()));
                if (!cardView_answer.isChecked()) {
                    cardView_answer.setChecked(true);
                    Drawable icon;
                    ColorStateList color;
                    if (answers.get(getAdapterPosition()).isCorrect) {
                        color = context.getColorStateList(R.color.green);
                        icon = AppCompatResources.getDrawable(context, R.drawable.ic_check);
                    } else {
                        color = context.getColorStateList(R.color.red);
                        icon = AppCompatResources.getDrawable(context, R.drawable.ic_close);
                    }
                    cardView_answer.setCheckedIcon(icon);
                    cardView_answer.setStrokeColor(color);
                    cardView_answer.setCheckedIconTint(color);
                }
                else {
                    cardView_answer.setChecked(false);
                    cardView_answer.setStrokeColor(context.getColorStateList(R.color.light_colorOnPrimary));
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
