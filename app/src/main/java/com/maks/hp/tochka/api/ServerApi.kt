package com.maks.hp.tochka.api

import com.maks.hp.tochka.gitsearch.entity.Entity
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("search/users")
    fun loadData(
            @Query("q") q: String
    ): Single<Entity>

    companion object {
        fun create(): ServerApi {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()
            return retrofit.create(ServerApi::class.java)
        }
    }
}