package com.fax.cursotestingaris.product_list.data.remote

import com.fax.cursotestingaris.product_list.data.remote.response.ProductsResponse
import com.fax.cursotestingaris.product_list.data.remote.response.PromotionsResponse
import retrofit2.http.GET

interface MarketApiService {

    @GET("data/products.json")
    suspend fun getProducts(): ProductsResponse

    @GET("data/promotions.json")
    suspend fun getPromotions(): PromotionsResponse
}
