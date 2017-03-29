package com.example.ilya.githubapi;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ilya on 3/28/17.
 */

public class RepoFactory {

    public static String user;
    private  Retrofit REPO_INSTANCE;

    RepoFactory(String user){
        this.user = user;
        REPO_INSTANCE = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/"+this.user+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    Retrofit retrofitRepo(){
        Log.d("123", user);return REPO_INSTANCE;
    }

}
