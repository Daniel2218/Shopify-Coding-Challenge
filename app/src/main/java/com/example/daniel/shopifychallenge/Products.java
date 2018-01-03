package com.example.daniel.shopifychallenge;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Daniel on 2017-12-29.
 */

public class Products {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Product> products;

    public Products(JSONObject json) {
        products = new ArrayList<Product>();

        if(json.has("products")) {
            try {
                setProducts(json.getJSONArray("products"));
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setProducts(JSONArray products) throws JSONException {
        String src;
        HashMap<String,String> productDetails;
        JSONArray images;

        for (int i = 0; i < products.length(); i++) {
            productDetails = new HashMap<String, String>();
            JSONObject item = products.getJSONObject(i);

            images = item.getJSONArray("images");
            src = images.getJSONObject(0).getString("src");

            productDetails.put("Title", item.getString("title"));
            productDetails.put("Description", item.getString("body_html"));
            productDetails.put("Vendor", item.getString("vendor"));
            Log.i(TAG, item.getString("tags"));
            productDetails.put("Tags", item.getString("tags"));
            productDetails.put("Product Type", item.getString("product_type"));

            this.products.add(new Product(productDetails, saveImageAsBitmap(src)));
        }
    }

    private Bitmap saveImageAsBitmap(String imageUrl) {
        URL url = null;
        Bitmap bmp = null;
        try {
            url = new URL(imageUrl);
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Product> getProducts() {
        return this.products;
    }

    static class Product implements Parcelable {
        public HashMap<String,String> productDetails;
        public Bitmap image;

        public Product(HashMap<String,String> productDetails, Bitmap image) {
            this.productDetails = productDetails;
            this.image = image;
        }

        protected Product(Parcel in) {
            image = in.readParcelable(Bitmap.class.getClassLoader());
            productDetails = (HashMap) in.readSerializable();
        }

        public static final Creator<Product> CREATOR = new Creator<Product>() {
            @Override
            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }

            @Override
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.image, i);
            parcel.writeSerializable(this.productDetails);
        }
    }
}
