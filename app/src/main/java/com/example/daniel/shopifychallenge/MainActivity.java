package com.example.daniel.shopifychallenge;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.daniel.shopifychallenge.utilities.NetworkUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        LoaderCallbacks<Products>,
        ShopifyDataAdapter.ShopifyDataAdapterOnClickHandler{
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar mProgressBar;
    private RecyclerView mItemsList;
    private TextView mErrorMessage;
    private ShopifyDataAdapter mShopifyDataAdapter;
    private static final int SHOPIFY_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItemsList = (RecyclerView) findViewById(R.id.items_list);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mErrorMessage = (TextView) findViewById((R.id.error_message));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mItemsList.setLayoutManager(layoutManager);
        mItemsList.setHasFixedSize(true);

        mShopifyDataAdapter = new ShopifyDataAdapter(this);
        mItemsList.setAdapter(mShopifyDataAdapter);

        Bundle bundleForLoader =  null;
        LoaderManager.LoaderCallbacks<Products> callback = MainActivity.this;

        getSupportLoaderManager().initLoader(SHOPIFY_LOADER_ID, bundleForLoader, callback);
    }

    @Override
    public Loader<Products> onCreateLoader(int i, Bundle bundle) {

        return new AsyncTaskLoader<Products>(this) {
            Products mShopifyData = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                if(mShopifyData != null) {
                    deliverResult(mShopifyData);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public Products loadInBackground() {
                URL shopifyUrl = NetworkUtils.buildUrl();

                Log.i(TAG, shopifyUrl.toString());

                try {
                    String response = NetworkUtils.getResponseFromHttpUrl(shopifyUrl);
                    JSONObject json = new JSONObject(response);
                    return new Products(json);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            /**
             * Sends the result of the load to the registered listener.
             *
             * @param data The result of the load
             */
            public void deliverResult(Products data) {
                mShopifyData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Products> loader, Products products) {
        hideProgressBar();
        mShopifyDataAdapter.setShopifyData(products);

        if(products == null) {
            showErrorMessage();
        } else {
            showShopifyData();
        }
    }

    @Override
    public void onLoaderReset(Loader<Products> loader) { }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void showShopifyData() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mItemsList.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mItemsList.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int index) {
        Products.Product product = mShopifyDataAdapter.getProduct(index);
        Context context = this;
        Class destinationClass = ProductDetails.class;
        Intent intentToStartProductDetailsActivity = new Intent(context, destinationClass);

        intentToStartProductDetailsActivity.putExtra("product", product);
        startActivity(intentToStartProductDetailsActivity);
    }
}
