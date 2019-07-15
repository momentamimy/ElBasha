package com.ElBasha.mob.app.Retrofit;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ELBashaApi {

    @POST("products")
    Call<List<ProductModel>> getDataByValue(
            @Body RamBodyModel ramBodyModel);

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
