package com.example.taskappsfornexus.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.taskappsfornexus.R; // Assuming this is your project's R class
import com.example.taskappsfornexus.dataclass.ApiResponse;
import java.util.List;
import java.util.Set;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static List<ApiResponse> newsList;
    private SharedPreferences sharedPreferences;
    private static OnItemClickListener listener;
    private Set<String> readingHistory;

    public NewsAdapter(List<ApiResponse> newsList, Context context, Set<String> readingHistory) {
        this.newsList = newsList;
        this.sharedPreferences = context.getSharedPreferences("ReadingHistory", Context.MODE_PRIVATE);
        this.readingHistory = readingHistory;
    }

    public interface OnItemClickListener {
        void onItemClick(ApiResponse.Data newsItemData);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApiResponse.Data newsItemData = newsList.get(position).getData();

        if (!readingHistory.contains(newsItemData.getId())) {
            // News item is not in reading history, proceed to bind data
            holder.newsTitle.setText(newsItemData.getTitle());
            holder.newsSummary.setText(newsItemData.getSummary());
            // Load image using Glide
            Glide.with(holder.itemView.getContext())
                    .load(newsItemData.getImage())
                    .into(holder.newsImage);

            // Set onClickListener to save reading history
            holder.itemView.setOnClickListener(v -> {
                // Save the news item ID to SharedPreferences
                saveReadingHistory(newsItemData.getId());
            });
        } else {
            // News item is in reading history, hide or handle accordingly
            holder.itemView.setVisibility(View.GONE); // Hide the item
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        TextView newsTitle;
        TextView newsSummary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Set onClickListener to save reading history
            itemView.setOnClickListener(v -> {
                // Check if the listener is not null and call onItemClick
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(newsList.get(position).getData());
                    }
                }
            });
            newsImage = itemView.findViewById(R.id.newsImage);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsSummary = itemView.findViewById(R.id.newsSummary);
        }
    }

    private void saveReadingHistory(String newsItemId) {
        // Retrieve the existing reading history set from SharedPreferences
        Set<String> readingHistory = sharedPreferences.getStringSet("readingHistory", null);

        // Add the current news item ID to the set

            readingHistory.add(newsItemId);


        // Save the updated set back to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("readingHistory", readingHistory);
        editor.apply();
    }
}
