package com.genericsol.quickcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.genericsol.quickcart.R;
import com.genericsol.quickcart.model.CategoryModel;

import java.util.List;

public class AdminCategoryAdapter extends RecyclerView.Adapter<AdminCategoryAdapter.ViewHolder> implements CategoryAdapter.OnItemClickListener {
    private List<CategoryModel> categories;
    private Context context;

    public AdminCategoryAdapter(Context context, List<CategoryModel> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragement_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel category = categories.get(position);
        holder.categoryNameTextView.setText(category.getName());

        Glide.with(context)
                .load(category.getImageLink())
                .into(holder.categoryImageView);

        // Set an OnClickListener for the category items
        holder.itemView.setOnClickListener(v -> {
            // Handle item click
            onItemClick(category);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onItemClick(CategoryModel category) {
        // Handle the item click for admin category
        // You can add your specific logic here
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryNameTextView;
        public ImageView categoryImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.textViewCategoryName);
            categoryImageView = itemView.findViewById(R.id.ViewImageCategory);
        }
    }
}
