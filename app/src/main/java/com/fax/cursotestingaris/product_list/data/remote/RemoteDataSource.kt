package com.fax.cursotestingaris.product_list.data.remote

import com.fax.cursotestingaris.core.domain.model.AppError
import com.fax.cursotestingaris.product_list.data.remote.response.ProductResponse
import com.fax.cursotestingaris.product_list.data.remote.response.PromotionResponse
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val miniMarketApiService: MarketApiService) {

    suspend fun getProducts(): Result<List<ProductResponse>> {
        return try {
            val response = miniMarketApiService.getProducts()
            Result.success(response.products)
        } catch (e: Exception) {
            return Result.failure(mapToDomainError(e))
        }
    }

    suspend fun getPromotions() : Result<List<PromotionResponse>> {
        return try{
            val response = miniMarketApiService.getPromotions()
            Result.success(response.promotions)
        } catch (e: Exception) {
            return Result.failure(mapToDomainError(e))
        }
    }

    private fun mapToDomainError(e: Exception): AppError {
        return when (e) {
            is UnknownHostException -> AppError.NetworkError
            is SocketTimeoutException -> AppError.NetworkError
            is IOException -> AppError.NetworkError
            is HttpException -> {
                when (e.code()) {
                    404 -> AppError.NotFoundError
                    500 -> AppError.ServerError
                    else -> AppError.NetworkError
                }
            }

            else -> AppError.UnknownError(e.message)
        }
    }
}