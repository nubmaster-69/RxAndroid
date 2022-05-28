package com.hisu.rx_android_call_api;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "https://simple-books-api.glitch.me/";

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService.class);

    @GET("books")
    Observable<List<Book>> getBooks();

    @GET("books") //e.g: https://simple-books-api.glitch.me/books?type=fiction
    Observable<List<Book>> getBooksByType(@Query("type") String type);
}