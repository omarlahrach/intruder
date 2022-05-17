package com.ailyan.intrus.ui.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ailyan.intrus.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    private final Context context;
    private final List<Integer> levels;
    private final LayoutInflater mInflater;
    private LevelAdapter.ItemClickListener mClickListener;

    public LevelAdapter(Context context, List<Integer> levels) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.levels = levels;
    }

    @NonNull
    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_level, parent, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        ViewGroup.MarginLayoutParams lpv = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        lp.height = parent.getMeasuredHeight() / 2 - lpv.topMargin * 2;
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LevelAdapter.ViewHolder holder, int position) {
        Integer level = levels.get(position);
        holder.textView_level.setText(context.getResources().getString(R.string.level_title, level + 1));
        switch (level) {
            case 0:
                holder.cardView_level.setCardBackgroundColor(context.getColor(R.color.blue));
                break;
            case 1:
                holder.cardView_level.setCardBackgroundColor(context.getColor(R.color.pink));
                break;
            case 2:
                holder.cardView_level.setCardBackgroundColor(context.getColor(R.color.green));
                break;
            case 3:
                holder.cardView_level.setCardBackgroundColor(context.getColor(R.color.amber));
                break;
        }
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
