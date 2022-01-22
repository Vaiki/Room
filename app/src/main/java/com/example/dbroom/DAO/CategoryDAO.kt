package com.example.dbroom.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dbroom.models.CategoryModel


@Dao
interface CategoryDAO {

    @Insert
    suspend fun insertCategory(categoryModel: CategoryModel)

    @Update
    suspend fun updateCategory(categoryModel: CategoryModel)

    @Delete
    suspend fun deleteCategory(categoryModel: CategoryModel)

    @Query("DELETE FROM CATEGORY_DATA_TABLE")
    suspend fun deleteAllCategory()

    @Query("SELECT * FROM CATEGORY_DATA_TABLE")
    fun getAllCategory(): LiveData<List<CategoryModel>>
}