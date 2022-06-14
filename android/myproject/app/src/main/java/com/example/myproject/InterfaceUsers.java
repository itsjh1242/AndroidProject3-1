package com.example.myproject;

import android.graphics.ColorSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InterfaceUsers {
    // 데이터 받아오기
    @GET("/users/{userId}")
    Call<ArrayList<ModelUsers>> getUserData(@Path("userId") String userId); // self Define

    @GET("/users/getUserChk/1")
    Call<ArrayList<ModelUsers>> getUserData2(); // self Define

    @FormUrlEncoded
    @POST("/users/")
    Call<ArrayList<ModelUsers>> postUserData(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @PATCH("{food_name}")
    Call<ColorSpace.Model> patchFoodData(@FieldMap HashMap<String, Object> param , @Path("food_name") String id);

    @DELETE("/items/{itemId}")
    Call<Void> deleteFoodData(@Path("itemId") int itemId);
}
