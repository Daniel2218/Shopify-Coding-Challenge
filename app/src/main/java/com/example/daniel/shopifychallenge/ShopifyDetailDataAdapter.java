package com.example.daniel.shopifychallenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Daniel on 2017-12-30.
 */

public class ShopifyDetailDataAdapter extends RecyclerView.Adapter<ShopifyDetailDataAdapter.ShopifyDetailDataAdapterViewHolder> {
    private Products.Product product;

    @Override
    public ShopifyDetailDataAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.shopify_product_detail_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = layoutInflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ShopifyDetailDataAdapter.ShopifyDetailDataAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopifyDetailDataAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(product == null) {
            return 0;
        }

        return product.productDetails.size();
    }

    public void setProduct(Products.Product product) {
        this.product = product;
    }

    public class ShopifyDetailDataAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mDetailKeytTextView;
        public final TextView mDetailValueTextView;

        public ShopifyDetailDataAdapterViewHolder(View itemView) {
            super(itemView);

            mDetailKeytTextView = itemView.findViewById(R.id.prod_det_key);
            mDetailValueTextView = itemView.findViewById(R.id.prod_det_value);
        }
    }
}
