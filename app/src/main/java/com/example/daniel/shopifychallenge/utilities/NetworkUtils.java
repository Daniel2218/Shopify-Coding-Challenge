package com.example.daniel.shopifychallenge.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Daniel on 2017-12-29.
 */

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private final static String SHOPIFY_BASE_URL = "https://shopicruit.myshopify.com/admin/products.json";

    private final static String PAGE = "page";
    private final static String PAGE_VALUE = "1";

    private final static String ACCESS_TOKEN = "access_token";
    private final static String ACCESS_TOKEN_VALUE = "c32313df0d0ef512ca64d5b336a0d7c6";

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param locationQuery The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl() {
        Uri builtUri = Uri.parse(SHOPIFY_BASE_URL).buildUpon()
                .appendQueryParameter(PAGE, PAGE_VALUE)
                .appendQueryParameter(ACCESS_TOKEN, ACCESS_TOKEN_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
