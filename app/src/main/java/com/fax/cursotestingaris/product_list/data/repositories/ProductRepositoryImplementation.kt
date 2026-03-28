package com.fax.cursotestingaris.product_list.data.repositories

import com.fax.cursotestingaris.core.domain.coroutines.DispatchersProvider
import com.fax.cursotestingaris.product_list.data.remote.RemoteDataSource
import com.fax.cursotestingaris.product_list.domain.models.Product
import com.fax.cursotestingaris.product_list.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImplementation @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val dispatchers: DispatchersProvider
) : ProductRepository {
    override fun getProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getProductById(id: String): Flow<Product?> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshProducts() {
        withContext(dispatchers.io){
            remoteDataSource.getProducts()
        }
    }
}