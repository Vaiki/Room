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
import com.example.dbroom.models.ProductModel
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
        productAdapter =
            ProductAdapter({ productModel: ProductModel -> deleteProduct(productModel) },
                { productModel -> editProduct(productModel) })
        binding?.recyclerProducts?.adapter = productAdapter
        displayCategories()
    }

    private fun displayCategories() {
        productsViewModel?.allProducts?.observe(viewLifecycleOwner, Observer {
            productAdapter?.setList(it)// в адаптер загоняем данные с allProduct
            productAdapter?.notifyDataSetChanged()
        })
    }

    private fun deleteProduct(productModel: ProductModel) {
        productsViewModel?.deleteProduct(productModel)
    }

    private fun editProduct(productModel: ProductModel) {
        val panelEditProduct = PanelEditProduct()
        val parameters = Bundle()
        //передаем данные в фрагмент редактирования,
        // чтобы поля для редактирования были заполнены выбранным элементом

        parameters.putString("idProduct", productModel.id.toString())
        parameters.putString("nameProduct", productModel.name)
        parameters.putString("categoryProduct", productModel.category)
        parameters.putString("priceProduct", productModel.price)
        panelEditProduct.arguments = parameters

        panelEditProduct.show((context as FragmentActivity).supportFragmentManager, "editProduct")
    }

}