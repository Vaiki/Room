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
import com.example.dbroom.databinding.FragmentTabFiltersBinding
import com.example.dbroom.models.ProductModel
import com.example.dbroom.repositories.ProductRepository
import com.example.dbroom.viewModels.ProductFactory
import com.example.dbroom.viewModels.ProductViewModel


class TabFilters : Fragment() {
    private var binding: FragmentTabFiltersBinding? = null
    private var productRepository: ProductRepository? = null
    private var productViewModel: ProductViewModel? = null
    private var productFactory: ProductFactory? = null
    private var productAdapter: ProductAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_tab_filters, container, false)

        val productDao = MyDataBase.getInstance((context as FragmentActivity).application).ProductDAO
        productRepository = ProductRepository(productDao)
        productFactory = ProductFactory(productRepository!!)
        productViewModel = ViewModelProvider(this, productFactory!!).get(ProductViewModel::class.java)

        return binding?.root
    }

    private fun initRecyclerFilterProducts(){
        binding?.recyclerFilter?.layoutManager = LinearLayoutManager(context)
        productAdapter = ProductAdapter({ productModel: ProductModel -> deleteProduct(productModel) },
            { productModel -> editProduct(productModel) })
        binding?.recyclerFilter?.adapter = productAdapter

        displayFilterProducts()
    }

    private fun displayFilterProducts(){
        productViewModel?.getFilter("одежда", "5000")?.observe(viewLifecycleOwner, Observer {
            productAdapter?.setList(it)
            productAdapter?.notifyDataSetChanged()
        })
    }
    private fun deleteProduct(productModel: ProductModel) {
        productViewModel?.deleteProduct(productModel)
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