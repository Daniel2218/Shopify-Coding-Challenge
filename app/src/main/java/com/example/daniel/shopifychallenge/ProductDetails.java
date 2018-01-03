package com.example.daniel.shopifychallenge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {
    private ImageView mProdDetsImageView;
    private LinearLayout mLinearLayout;
    private RecyclerView mDetailsList;
    private ShopifyDetailDataAdapter detailsDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        mProdDetsImageView = (ImageView) findViewById(R.id.prod_det_image);
        mLinearLayout  = (LinearLayout) findViewById(R.id.prod_det_layout);
        mDetailsList = (RecyclerView) findViewById(R.id.details_list);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDetailsList.setLayoutManager(layoutManager);
        mDetailsList.setHasFixedSize(true);

        detailsDataAdapter = new ShopifyDetailDataAdapter();

        Intent intent = getIntent();
        if(intent.hasExtra("product")) {
            Products.Product product = intent.getParcelableExtra("product");
            mProdDetsImageView.setImageBitmap(product.image);

            detailsDataAdapter.setProduct(product);
            mDetailsList.setAdapter(detailsDataAdapter);
        }
    }
}
