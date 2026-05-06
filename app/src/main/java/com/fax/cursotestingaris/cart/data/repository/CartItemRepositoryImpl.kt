package com.fax.cursotestingaris.cart.data.repository

import com.fax.cursotestingaris.cart.data.mapper.toDomain
import com.fax.cursotestingaris.cart.data.mapper.toEntity
import com.fax.cursotestingaris.cart.domain.model.CartItem
import com.fax.cursotestingaris.cart.domain.repository.CartItemRepository
import com.fax.cursotestingaris.core.domain.model.AppError
import com.fax.cursotestingaris.product_list.data.local.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartItemRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : CartItemRepository {
    override fun getCartItems(): Flow<List<CartItem>> {
        return localDataSource.getAllCartItems()
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    override suspend fun addToCart(productId: String, quantity: Int) {
        val existingItem = localDataSource.getCartItemById(productId)
        if(existingItem != null){
            val newQuantity = existingItem.quantity + quantity
            localDataSource.updateCartItem(existingItem.copy(quantity = newQuantity))
        } else {
            localDataSource.insertCartItem(CartItem(productId, quantity).toEntity())
        }
    }

    override suspend fun removeFromCart(productId: String) {
        val item = localDataSource.getCartItemById(productId) ?: throw AppError.NotFoundError
        localDataSource.deleteCartItem(item)
    }

    override suspend fun updateQuantity(productId: String, quantity: Int) {
        val item = localDataSource.getCartItemById(productId) ?: throw AppError.NotFoundError
        //localDataSource.updateCartItem(CartItem(productId, quantity).toEntity())
        localDataSource.updateCartItem(item.copy(quantity = quantity))
    }

    override suspend fun clearCart() {
        localDataSource.clearCartItem()
    }
}