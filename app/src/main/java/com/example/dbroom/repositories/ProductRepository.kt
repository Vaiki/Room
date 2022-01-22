package com.example.dbroom.repositories

import androidx.lifecycle.LiveData
import com.example.dbroom.DAO.ProductDAO
import com.example.dbroom.models.ProductModel

class ProductRepository (private val productDAO: ProductDAO) {

    val products = productDAO.getAllProducts()

    fun getFilter (nameCategory:String, priceProduct:String):
            LiveData<List<ProductModel>> {
        return productDAO.getFilter(nameCategory, priceProduct)
    }


    suspend fun insertProduct(productModel: ProductModel){
        productDAO.insertProduct(productModel)
    }

    suspend fun updateProduct(productModel: ProductModel){
        productDAO.updateProduct(productModel)
    }

    suspend fun deleteProduct(productModel: ProductModel) {
        productDAO.deleteProduct(productModel)
    }

    suspend fun deleteAllProducts(){
        productDAO.deleteAllProducts()
    }
}