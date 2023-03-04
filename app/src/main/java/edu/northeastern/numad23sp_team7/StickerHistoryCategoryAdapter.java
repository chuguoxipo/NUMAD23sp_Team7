package edu.northeastern.numad23sp_team7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StickerHistoryCategoryAdapter extends RecyclerView.Adapter<StickerHistoryCategoryAdapter.CategoryViewHoler> {
    private ArrayList<String> categories;
    private ArrayList<Integer> counts;
    private Context context;

    public StickerHistoryCategoryAdapter(ArrayList<String> categories, ArrayList<Integer> counts, Context context) {
        this.categories = categories;
        this.counts = counts;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticker_history_category_card, parent, false);
        return new CategoryViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHoler holder, int position) {
        holder.textCategoryItem.setText(categories.get(position));
        holder.textCategoryCount.setText(String.valueOf(counts.get(position)));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHoler extends RecyclerView.ViewHolder {
        private TextView textCategoryItem, textCategoryCount;

        public CategoryViewHoler(@NonNull View itemView) {
            super(itemView);

            textCategoryItem = itemView.findViewById(R.id.textViewStickerHistoryCategoryItem);
            textCategoryCount = itemView.findViewById(R.id.textViewStickerHistoryCategoryCount);
        }
    }
}
