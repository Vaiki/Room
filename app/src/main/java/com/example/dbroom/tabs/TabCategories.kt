package com.example.dbroom.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import com.example.dbroom.R
import com.example.dbroom.data.MyDataBase
import com.example.dbroom.databinding.FragmentTabCategoriesBinding
import com.example.dbroom.repositories.CategoryRepository
import com.example.dbroom.viewModels.CategoryFactory
import com.example.dbroom.viewModels.CategoryViewModel


class TabCategories : Fragment() {

    private var binding: FragmentTabCategoriesBinding? = null
    private var categoryRepository: CategoryRepository? = null
    private var categoryFactory: CategoryFactory? = null
    private var categoryViewModel: CategoryViewModel? = null
    private var categoryAdapter: CategoryAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tab_categories, container, false)
        // Inflate the layout for this fragment
        val categoryDAO =
            MyDataBase.getInstance((context as FragmentActivity).application).CategoryDAO
        categoryRepository = CategoryRepository(categoryDAO)
        categoryFactory = CategoryFactory(categoryRepository!!)
        categoryViewModel =
            ViewModelProvider(this, categoryFactory!!).get(CategoryViewModel::class.java)
        initRecyclerCategories()
        displayCategories()

        binding?.deleteAllCategories?.setOnClickListener(View.OnClickListener { categoryViewModel?.deleteAll() })
        return binding?.root
    }

    private fun initRecyclerCategories() {
        binding?.recyclerCategories?.layoutManager = LinearLayoutManager(context)
        categoryAdapter = CategoryAdapter()
        binding?.recyclerCategories?.adapter = categoryAdapter
    }

    private fun displayCategories() {
        categoryViewModel?.allCategory?.observe(viewLifecycleOwner, Observer {
            categoryAdapter?.setList(it)// в адаптер загоняем данные с allCategory
            categoryAdapter?.notifyDataSetChanged()
        })
    }

}