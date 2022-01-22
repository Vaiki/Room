package com.example.dbroom.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CATEGORY_DATA_TABLE") //название таблицы
data class CategoryModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id") // описываем столбцы таблицы
    var id: Int,
    @ColumnInfo(name = "category_name")
    var name: String
)