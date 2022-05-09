package com.ailyan.intrus.ui.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    private final List<LevelEntity> levels;
    private final LayoutInflater mInflater;
    private LevelAdapter.ItemClickListener mClickListener;

    public LevelAdapter(Context context, List<LevelEntity> levels) {
        this.mInflater = LayoutInflater.from(context);
        this.levels = levels;
    }

    @NonNull
    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_level, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.ViewHolder holder, int position) {
        LevelEntity level = levels.get(position);
        holder.textView_level.setText(R.string.level_name + level.id);
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MaterialCardView cardView_level;
        TextView textView_level;

        ViewHolder(View itemView) {
            super(itemView);
            cardView_level = itemView.findViewById(R.id.cardView_level);
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
