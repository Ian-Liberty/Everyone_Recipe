package com.example.everyone_recipe.retrofit2;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SignupInterface {
    //인터페이스에서 사용할 BASE URL
    String LOGIN_URL = "http://52.78.164.239/everyone_recipe/";

    @FormUrlEncoded
    @POST("signup.php")
    Call<String> signup(
            @Field("email") String email,
            @Field("nickname") String nickname,
            @Field("password") String password
    );

}
