package com.example.dbroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.dbroom.databinding.ActivityMainBinding
import com.example.dbroom.tabs.TabCategories
import com.example.dbroom.tabs.TabFilters
import com.example.dbroom.tabs.TabPanel
import com.example.dbroom.tabs.TabProducts
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        supportFragmentManager.beginTransaction().replace(R.id.content, TabPanel()).commit()


        binding?.bottomNav?.selectedItemId = R.id.panelItemBottomNav
        binding?.bottomNav?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.panelItemBottomNav -> supportFragmentManager.beginTransaction()
                    .replace(R.id.content, TabPanel()).commit()
                R.id.catalogProductsItemBottomNav -> supportFragmentManager.beginTransaction()
                    .replace(R.id.content, TabProducts()).commit()
                R.id.catalogClothesItemBottomNav -> supportFragmentManager.beginTransaction()
                    .replace(R.id.content, TabFilters()).commit()
                R.id.catalogCategoriesItemBottomNav -> supportFragmentManager.beginTransaction()
                    .replace(R.id.content, TabCategories()).commit()
            }
            return@setOnItemSelectedListener true

        }

    }


}