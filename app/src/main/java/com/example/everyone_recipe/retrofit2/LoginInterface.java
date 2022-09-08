package com.example.everyone_recipe.retrofit2;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {

    String LOGIN_URL = "http://52.78.164.239/everyone_recipe/";

    @FormUrlEncoded
    @POST("login.php")
    Call<String> login(
            @Field("email") String email,
            @Field("password") String password
    );
}
