package com.example.daniel.shopifychallenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.HashMap;

/**
 * Created by Daniel on 2017-12-29.
 */

public class ShopifyDataAdapter extends RecyclerView.Adapter<ShopifyDataAdapter.ShopifyDataAdapterViewHolder> {
    private Products shopifyData;
    private final ShopifyDataAdapterOnClickHandler mOnClickHandler;

    public interface ShopifyDataAdapterOnClickHandler {
        void onClick(int index);
    }

    public ShopifyDataAdapter(ShopifyDataAdapterOnClickHandler mOnClickHandler) {
        this.mOnClickHandler = mOnClickHandler;
    }

    public class ShopifyDataAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mProductTextView;
        public final ImageView mProductImageView;
        public final TextView mProductDescTextView;

        public ShopifyDataAdapterViewHolder(View itemView) {
            super(itemView);

            mProductTextView = itemView.findViewById(R.id.product_item);
            mProductDescTextView = itemView.findViewById(R.id.product_item_desc);
            mProductImageView = itemView.findViewById(R.id.product_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPostion = getAdapterPosition();
            mOnClickHandler.onClick(adapterPostion);
        }
    }

    @Override
    public ShopifyDataAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.shopify_product_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ShopifyDataAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopifyDataAdapterViewHolder holder, int position) {
        Products.Product product = shopifyData.getProducts().get(position);
        HashMap<String,String> productDetails = product.productDetails;

        holder.mProductTextView.setText(productDetails.get("Title"));
        holder.mProductDescTextView.setText(productDetails.get("Description"));

        Bitmap bitmap = product.image;
        holder.mProductImageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if(shopifyData == null) {
            return 0;
        }
        return shopifyData.getProducts().size();
    }

    public Products.Product getProduct(int index) {
        return shopifyData.getProducts().get(index);
    }

    public void setShopifyData(Products products) {
        shopifyData = products;
        notifyDataSetChanged();
    }
}
