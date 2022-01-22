package com.example.dbroom.repositories

import com.example.dbroom.DAO.CategoryDAO
import com.example.dbroom.models.CategoryModel

class CategoryRepository(private val categoryDAO: CategoryDAO) {

    val category = categoryDAO.getAllCategory()

    suspend fun insertCategory(categoryModel: CategoryModel){
        categoryDAO.insertCategory(categoryModel)
    }

    suspend fun updateCategory(categoryModel: CategoryModel){
        categoryDAO.updateCategory(categoryModel)
    }
    suspend fun deleteCategory(categoryModel: CategoryModel){
        categoryDAO.deleteCategory(categoryModel)
    }
    suspend fun deleteAllCategory(){
        categoryDAO.deleteAllCategory()
    }

}