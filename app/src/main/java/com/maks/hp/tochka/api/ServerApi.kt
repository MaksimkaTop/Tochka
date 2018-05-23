package com.maks.hp.tochka.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maks.hp.tochka.gitsearch.GitInfo
import com.maks.hp.tochka.gitsearch.entity.Entity
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import kotlin.collections.ArrayList

interface ServerApi {

    @GET("search/users")
    fun loadData(
            @Query("q") q: String,
            @Query("sort") sort: String,
            @Query("order") order: String
    ): Single<Entity>


    companion object {
        fun create(): ServerApi {
val gson : Gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()
            return retrofit.create(ServerApi::class.java)
        }
    }
}