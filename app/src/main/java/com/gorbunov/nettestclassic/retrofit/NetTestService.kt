package com.gorbunov.nettestclassic.retrofit

import com.gorbunov.nettestclassic.model.NetTestResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Базовый адрес при создании экземпляра ретрофита
 */
private const val BASE_URL = "https://raw.githubusercontent.com/netology-code/rn-task/master/"


/**
 * Процесс создания экземпляра ретрофита с библиотекой сериализации
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 * Интерфейс для описания запросов
 */
interface NetTestService {
    /**
     * Классический GET запрос на JSON который сразу дессериализует данные в указанный формат
     */
    @GET("netology.json")
    suspend fun getData(): NetTestResponse

}

/**
 * Ленивое создание экземпляра
 */
object NetTestApi{
    val retrofitService: NetTestService by lazy {
        retrofit.create(NetTestService::class.java)
    }
}



