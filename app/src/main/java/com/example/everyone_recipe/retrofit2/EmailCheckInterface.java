package com.example.everyone_recipe.retrofit2;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface EmailCheckInterface {
//인터페이스에서 사용할 BASE URL
    String LOGIN_URL = "http://52.78.164.239/everyone_recipe/";
//@POST("접근할 PHP")
    //@Field("POST키") String ""
    @FormUrlEncoded
    @POST("ER_email_check.php")
    Call<String> getEmail(
            @Field("useremail") String useremail
    );

    @FormUrlEncoded
    @POST("mailSender.php")
    Call<String> getaut_code(
            @Field("useremail") String useremail
    );

}
