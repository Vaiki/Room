package com.example.dbroom.tabs

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dbroom.R
import com.example.dbroom.data.MyDataBase
import com.example.dbroom.databinding.PanelEditCategoryBinding
import com.example.dbroom.repositories.CategoryRepository
import com.example.dbroom.viewModels.CategoryFactory
import com.example.dbroom.viewModels.CategoryViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PanelEditCategory : BottomSheetDialogFragment(), View.OnKeyListener {

    private var binding: PanelEditCategoryBinding? = null
    private var categoryRepository: CategoryRepository? = null
    private var categoryViewModel: CategoryViewModel? = null
    private var factory: CategoryFactory? = null
    private var idCategory: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.panel_edit_category, container, false)

        // загоняем id выбранной категории в новую переменную
        idCategory = arguments?.getString("idCategory")?.toInt()

        //заполняем поля редактирования выбранным товаром в TabCategory
        binding?.editCategory?.setText(arguments?.getString("nameCategory").toString())

        val categoriesDao =
            MyDataBase.getInstance((context as FragmentActivity).application).CategoryDAO
        categoryRepository = CategoryRepository(categoriesDao)
        factory = CategoryFactory(categoryRepository!!)
        categoryViewModel = ViewModelProvider(this, factory!!).get(CategoryViewModel::class.java)

        binding?.editCategory?.setOnKeyListener(this)

        return binding?.root
    }

    override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
        when (view.id) {


            R.id.editCategory -> {
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    // в categoryModel с id которое передавали по Bundle - "idCategory",
                    // вписать изменения которые написаны в editCategory
                    categoryViewModel?.startUpdateProduct(
                        idCategory.toString().toInt(),
                        binding?.editCategory?.text?.toString()!!
                    )

                    binding?.editCategory?.setText("")

                    dismiss()

                    (context as FragmentActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.content, TabCategories()).commit()

                    return true
                }

            }
        }

        return false
    }

}