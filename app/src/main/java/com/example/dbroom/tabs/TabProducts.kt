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
import com.example.dbroom.R
import com.example.dbroom.data.MyDataBase
import com.example.dbroom.databinding.FragmentTabProductsBinding
import com.example.dbroom.repositories.ProductRepository
import com.example.dbroom.viewModels.CategoryViewModel
import com.example.dbroom.viewModels.ProductFactory
import com.example.dbroom.viewModels.ProductViewModel
import java.security.Provider


class TabProducts : Fragment() {
    private var binding: FragmentTabProductsBinding? = null
    private var productsRepository: ProductRepository? = null
    private var productsViewModel: ProductViewModel? = null
    private var productFactory: ProductFactory? = null
    private var productAdapter: ProductAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tab_products, container, false)
        val productDAO =
            MyDataBase.getInstance((context as FragmentActivity).application).ProductDAO
        productsRepository = ProductRepository(productDAO)
        productFactory = ProductFactory(productsRepository!!)
        productsViewModel =
            ViewModelProvider(this, productFactory!!).get(ProductViewModel::class.java)
        initRecyclerCategories()

        binding?.deleteAllProducts?.setOnClickListener(View.OnClickListener { productsViewModel?.deleteAllProducts() })

        return binding?.root
    }

    private fun initRecyclerCategories() {
        binding?.recyclerProducts?.layoutManager = LinearLayoutManager(context)
        productAdapter = ProductAdapter()
        binding?.recyclerProducts?.adapter = productAdapter
        displayCategories()
    }

    private fun displayCategories() {
        productsViewModel?.allProducts?.observe(viewLifecycleOwner, Observer {
            productAdapter?.setList(it)// в адаптер загоняем данные с allProduct
            productAdapter?.notifyDataSetChanged()
        })
    }


}