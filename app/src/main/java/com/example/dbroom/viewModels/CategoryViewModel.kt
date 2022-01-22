package com.example.dbroom.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dbroom.models.CategoryModel
import com.example.dbroom.repositories.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel (private val categoryRepository: CategoryRepository) : ViewModel() {

    val allCategory = categoryRepository.category

    fun startInsert(nameCategory:String) {
        insert(CategoryModel(0, nameCategory))
    }

    fun startUpdateProduct(idCategory:Int, nameCategory:String) {
        updateProduct(CategoryModel(idCategory, nameCategory))
    }

    fun insert(categoryModel: CategoryModel) = viewModelScope.launch{

        categoryRepository.insertCategory(categoryModel)
    }

    fun updateProduct(categoryModel: CategoryModel) = viewModelScope.launch{

        categoryRepository.updateCategory(categoryModel)
    }

    fun delete(categoryModel: CategoryModel) = viewModelScope.launch{

        categoryRepository.deleteCategory(categoryModel)
    }

    fun deleteAll() = viewModelScope.launch{

        categoryRepository.deleteAllCategory()
    }

}