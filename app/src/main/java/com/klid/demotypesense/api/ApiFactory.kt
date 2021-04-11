package com.klid.demotypesense.api

import com.klid.demotypesense.api.model.PageModel
import com.klid.demotypesense.api.model.Student
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

/**
 * @author Ivan Kaptue
 */

private const val BASE_URL = "http://10.0.2.2:8080/"

interface ApiService {

    @POST("students")
    suspend fun create(@Body student: Student): Response<Student>

    @PUT("students/{id}")
    suspend fun update(@Path("id") id: Long, @Body student: Student): Response<Student>

    @DELETE("students/{id}")
    suspend fun delete(@Path("id") id: Long): Response<Void>

    @GET("students/{id}")
    suspend fun findById(@Path("id") id: Long): Response<Student>

    @GET("students")
    suspend fun findAll(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PageModel<Student>>

    @GET("students/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PageModel<Student>>

}

object ApiFactory {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            println(chain.request())
            chain.proceed(chain.request())
        }.build()

    val apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(ApiService::class.java)
}
