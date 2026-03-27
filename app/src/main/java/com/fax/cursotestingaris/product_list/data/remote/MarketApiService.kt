package com.fax.cursotestingaris.product_list.data.remote

import com.fax.cursotestingaris.product_list.data.remote.response.ProductResponse
import com.fax.cursotestingaris.product_list.data.remote.response.PromotionsResponse
import retrofit2.http.GET

interface MarketApiService {

    @GET("data/products.json")
    suspend fun getProducts(): ProductResponse

    @GET("data/promotions.json")
    suspend fun getPromotions(): PromotionsResponse
}
