package com.example.mohammed.baking_app.NetworkUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReceipeDataService {
    private static Retrofit retrofit = null;

    public static Retrofit getDefaultInstance() {
        if (retrofit == null)
            return retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantLinks.ReceipeBaseLink)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }
}
