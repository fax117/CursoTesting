package com.fax.cursotestingaris.product_list.data.local

import com.fax.cursotestingaris.cart.data.local.database.dao.CartItemDao
import com.fax.cursotestingaris.cart.data.local.database.entity.CartItemEntity
import com.fax.cursotestingaris.product_list.data.local.database.dao.ProductDao
import com.fax.cursotestingaris.product_list.data.local.database.dao.PromotionDao
import com.fax.cursotestingaris.product_list.data.local.database.entity.ProductEntity
import com.fax.cursotestingaris.product_list.data.local.database.entity.PromotionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalDataSource @Inject constructor(
    private val productDao: ProductDao,
    private val promotionDao: PromotionDao,
    private val cartItemDao: CartItemDao
) {

    fun getAllProducts(): Flow<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun getProductById(productId: String): Flow<ProductEntity?> {
        return productDao.getAllProductById(productId)
    }

    fun getAllPromotions(): Flow<List<PromotionEntity>> {
        return promotionDao.getAllPromotions()
    }

    suspend fun saveProducts(productsEntity: List<ProductEntity>) {
        productDao.replaceAll(productsEntity)
    }

    suspend fun savePromotions(promotionsEntity: List<PromotionEntity>) {
        promotionDao.replaceAll(promotionsEntity)
    }

    //Cart
    fun getAllCartItems(): Flow<List<CartItemEntity>> = cartItemDao.getAllCartItems()

    suspend fun getCartItemById(productId: String): CartItemEntity? =
        cartItemDao.getCartItemById(productId)

    suspend fun updateCartItem(cartItemEntity: CartItemEntity): Result<Unit>{
        return try {
            cartItemDao.updateCartItem(cartItemEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun insertCartItem(cartItemEntity: CartItemEntity): Result<Unit>{
        return try{
            cartItemDao.insertCartItem(cartItemEntity)
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun deleteCartItem(cartItemEntity: CartItemEntity): Result<Unit>{
        return try {
            cartItemDao.deleteCartItem(cartItemEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun clearCartItem(): Result<Unit>{
        return try {
            cartItemDao.clearCart()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}