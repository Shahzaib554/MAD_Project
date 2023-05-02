package com.genericsol.quickcart;

import java.util.List;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genericsol.quickcart.Product;

import org.w3c.dom.Text;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_view, parent, false);
        return new ProductViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productImage.setImageResource(product.getImageResource());
        holder.productTitle.setText(product.getTitle());
        holder.productPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImage;
        public TextView productTitle;
        public TextView productPrice;

        public ProductViewHolder(View view) {
            super(view);
            productImage = view.findViewById(R.id.appleProducts);
            productTitle = view.findViewById(R.id.ap_title);
            productPrice = view.findViewById(R.id.ap_description);
        }
    }
}

