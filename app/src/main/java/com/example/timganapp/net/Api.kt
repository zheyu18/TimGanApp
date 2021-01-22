package com.example.timganapp.net
import android.util.Log
import com.example.timganapp.repository.PublishedDate
import com.example.timganapp.repository.Result
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface Api {
    // http://qank.io/
    @GET("api/data/{type}/{pageSize}/{pageNumber}")
    fun getData(
        @Path("type") type: String,
        @Path("pageSize") pageSize: Int,
        @Path("pageNumber") pageNumber: Int
    ): Observable<Result>


    @GET("api/day/{year}/{month}/{day}")
    fun getDataByDate(
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("day") day: String
    )

    @GET("api/day/{date}")
    fun getDataByDate(@Path("date") date: String): Observable<ResponseBody>

    @GET("history")
    fun getHistory(): Observable<String>

    @GET("api/day/history")
    fun getPublishedDate(): Observable<PublishedDate>

    companion object {
        fun create(): Api {
            val loging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.i("api","message:$it" )
            })
            loging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(loging)
                .build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://gank.io/")
                .build()
            return retrofit.create(Api::class.java)
        }
    }


}