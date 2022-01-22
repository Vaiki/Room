package com.example.dbroom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dbroom.DAO.CategoryDAO
import com.example.dbroom.DAO.ProductDAO
import com.example.dbroom.models.CategoryModel
import com.example.dbroom.models.ProductModel

@Database(entities = [CategoryModel::class,ProductModel::class], version = 1) // перечисляем какие классы под таблицы
abstract class MyDataBase:RoomDatabase() {
   abstract val ProductDAO:ProductDAO
   abstract val CategoryDAO:CategoryDAO
    companion object{
        @Volatile
        private var INSTANCE : MyDataBase? = null
        fun getInstance(context: Context):MyDataBase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                      MyDataBase::class.java,
                        "database"
                    ).build()
                }
                return instance
            }
        }

    }
}