package com.ElBasha.mob.app.Retrofit;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by khaled-pc on 4/7/2019.
 */

public class retrofitHead {

   public final static String URL="https://elbashastores.com/wp-json/api/v1/";



   public static Retrofit headOfGetorPostReturnRes(){
       Gson gson = new GsonBuilder()
               .setLenient()
               .create();

       HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
       interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
       OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


       Retrofit retrofit=new Retrofit.Builder()
                   .baseUrl(URL)
                   .client(client)
                   .addConverterFactory(GsonConverterFactory.create(gson))
                   .build();

           return retrofit;
    }


    public static Retrofit headOfPost(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URL)
                .build();

        return retrofit;
    }

    public static Retrofit retrofitTimeOut(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .connectTimeout(520, TimeUnit.SECONDS)
                .writeTimeout(520, TimeUnit.SECONDS)
                .readTimeout(520, TimeUnit.SECONDS).build();



        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

}
