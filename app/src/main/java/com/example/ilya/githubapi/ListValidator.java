package com.example.ilya.githubapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by ilya on 3/28/17.
 */

interface ListValidator {

    @GET("repos")
    @Headers({
            "Accept: application/json"
    })
    Call<List<RepoValidate>> validate();

}
