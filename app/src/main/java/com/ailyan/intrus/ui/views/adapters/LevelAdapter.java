package com.ailyan.intrus.ui.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.databinding.FragmentLevelBinding;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    private final Context context;
    private final List<LevelEntity> levels;
    private final LayoutInflater mInflater;
    private LevelAdapter.ItemClickListener mClickListener;
    private final int rows;
    private final FragmentLevelBinding binding;

    public LevelAdapter(Context context, List<LevelEntity> levels, int rows, FragmentLevelBinding binding) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.levels = levels;
        this.rows = rows;
        this.binding = binding;
    }

    @NonNull
    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_level, parent, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        ViewGroup.MarginLayoutParams lpv = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        lp.height = parent.getMeasuredHeight() / rows - lpv.topMargin * 2;
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.ViewHolder holder, int position) {
        LevelEntity level = levels.get(position);
        holder.textView_level.setText(level.title);
        switch (level.id) {
            case 0:
                holder.cardView_level.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_level1));
                holder.indicator_progress.setIndicatorColor(context.getColor(R.color.dark_magenta));
                holder.indicator_progress.setTrackColor(context.getColor(R.color.amber));
                holder.textView_points.setTextColor(context.getColor(R.color.dark_magenta));
                holder.textView_level.setTextColor(context.getColor(R.color.dark_magenta));
                break;
            case 1:
                holder.cardView_level.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_level2));
                holder.indicator_progress.setIndicatorColor(context.getColor(R.color.dark_green));
                holder.indicator_progress.setTrackColor(context.getColor(R.color.green));
                holder.textView_points.setTextColor(context.getColor(R.color.dark_green));
                holder.textView_level.setTextColor(context.getColor(R.color.dark_green));
                break;
            case 2:
                holder.cardView_level.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_level3));
                holder.indicator_progress.setIndicatorColor(context.getColor(R.color.dark_amber));
                holder.indicator_progress.setTrackColor(context.getColor(R.color.amber));
                holder.textView_points.setTextColor(context.getColor(R.color.dark_amber));
                holder.textView_level.setTextColor(context.getColor(R.color.dark_amber));
                break;
            case 3:
                holder.cardView_level.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_level4));
                holder.indicator_progress.setIndicatorColor(context.getColor(R.color.dark_purple));
                holder.indicator_progress.setTrackColor(context.getColor(R.color.purple));
                holder.textView_points.setTextColor(context.getColor(R.color.dark_purple));
                holder.textView_level.setTextColor(context.getColor(R.color.dark_purple));
                break;
        }
        String points = level.score.resultObtained + "/" + level.score.maximumResult +  " points";
        holder.textView_points.setText(points);
        holder.indicator_progress.setMax(100);
        holder.indicator_progress.setProgress(level.score.getRate(), true);
        binding.loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView cardView_level;
        CircularProgressIndicator indicator_progress;
        TextView textView_points;
        TextView textView_level;

        ViewHolder(View itemView) {
            super(itemView);
            cardView_level = itemView.findViewById(R.id.cardView_level);
            indicator_progress = itemView.findViewById(R.id.indicator_progress);
            textView_points = itemView.findViewById(R.id.textView_points);
            textView_level = itemView.findViewById(R.id.textView_level);
            itemView.setOnClickListener(this);
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
