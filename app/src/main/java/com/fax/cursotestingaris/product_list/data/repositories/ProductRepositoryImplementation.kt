package com.fax.cursotestingaris.product_list.data.repositories

import android.util.Log
import com.fax.cursotestingaris.core.domain.coroutines.DispatchersProvider
import com.fax.cursotestingaris.product_list.data.local.LocalDataSource
import com.fax.cursotestingaris.product_list.data.mappers.toDomain
import com.fax.cursotestingaris.product_list.data.mappers.toEntity
import com.fax.cursotestingaris.product_list.data.remote.RemoteDataSource
import com.fax.cursotestingaris.product_list.data.remote.response.ProductResponse
import com.fax.cursotestingaris.product_list.domain.models.Product
import com.fax.cursotestingaris.product_list.domain.repositories.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImplementation @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,

    val dispatchers: DispatchersProvider
) : ProductRepository {

    private val refreshScope = CoroutineScope(SupervisorJob() + dispatchers.io)
    private val refreshMutex = Mutex()

    override fun getProducts(): Flow<List<Product>> {
        return localDataSource.getAllProducts()
            .map { productsEntity ->
                productsEntity.mapNotNull {
                    it.toDomain()
                }
            }
            .onStart {
                refreshScope.launch {
                    if (!refreshMutex.tryLock()) return@launch
                    try {
                        refreshProducts()
                    } catch (e: Exception) {
                        Log.e("ProductRepository", "Error refreshing products", e)
                    } finally {
                        refreshMutex.unlock()
                    }
                }
            }
            .catch {
                // TODO Implement error handling
            }
    }

    override fun getProductById(id: String): Flow<Product?> {
        return localDataSource.getProductById(id)
            .map { entity -> entity?.toDomain() }
            .catch { e ->
                throw(e)
            }
    }

    override suspend fun refreshProducts() {
        withContext(dispatchers.io) {
            val products: List<ProductResponse> = remoteDataSource.getProducts().getOrThrow()
            val productsEntity = products.map {
                it.toEntity()
            }
            localDataSource.saveProducts(productsEntity)
        }
    }
}