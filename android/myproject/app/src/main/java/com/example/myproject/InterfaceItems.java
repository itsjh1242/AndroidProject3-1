package com.example.myproject;

import android.graphics.ColorSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceItems {

    @GET("/items/selectItem/{storeId}") // 신선 상품 조회
    Call<ArrayList<ModelItems>> getFoodData2(@Path("storeId") int storeId); // self Define

    @GET("/items/selectDiscountItem/{storeId}") // 할인 음식 조회
    Call<ArrayList<ModelItems>> getFoodData(@Path("storeId") int storeId); // self Define

    @FormUrlEncoded
    @POST("/items/")
    Call<ArrayList<ModelItems>> postFoodData(@FieldMap HashMap<String, Object> param);

    @FormUrlEncoded
    @PATCH("{food_name}")
    Call<ColorSpace.Model> patchFoodData(@FieldMap HashMap<String, Object> param , @Path("food_name") String id);

    @DELETE("/items/{itemId}")
    Call<Void> deleteFoodData(@Path("itemId") int itemId);
}
