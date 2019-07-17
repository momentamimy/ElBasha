package com.ElBasha.mob.app.Retrofit;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface ELBashaApi {

    @POST("products")
    Call<List<ProductModel>> getDataByValue(
            @Header("Content-Type") String Accept,
            @Body RamBodyModel ramBodyModel);

    @FormUrlEncoded
    @POST("products")
    Call<List<ProductModel>> getDataByValue2(
            @Header("Content-Type") String Accept,
            @Field("ram") int ram,
            @Field("storage") int storage,
            @Field("battery") int battery,
            @Field("screen") int screen,
            @Field("processor") String processor,
            @Field("os") String os
    );


    @POST("products/price")
    Call<List<ProductModel>> getDataPriceRange(
            @Body RangeBodyModel rangeBodyModel);

    @POST("products/price")
    Call<List<ProductModel>> getDataMinPrice(
            @Body MinBodyModel minBodyModel);

    @POST("products/price")
    Call<List<ProductModel>> getDataMaxPrice(
            @Body MaxBodyModel maxBodyModel);


}
