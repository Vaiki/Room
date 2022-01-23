package com.example.dbroom.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dbroom.R
import com.example.dbroom.databinding.CategoryItemBinding
import com.example.dbroom.models.CategoryModel
import java.util.zip.Inflater

class CategoryAdapter (private val deleteCategory:(CategoryModel)->Unit,
                       private val editCategory:(CategoryModel)->Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    private val categoryList= ArrayList<CategoryModel>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryHolder {

        val binding: CategoryItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.category_item, parent, false)
        return CategoryHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryHolder, position: Int) {
       holder.bind(categoryList[position],deleteCategory,editCategory)
    }
fun setList(categories:List<CategoryModel>){
    categoryList.clear()
    categoryList.addAll(categories)
}
    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CategoryHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
fun bind(
    category:CategoryModel,
    deleteCategory:(CategoryModel)->Unit,
    editCategory:(CategoryModel)->Unit
) {

    binding.idCategory.text = category.id.toString()
    binding.nameCategory.text = category.name

    binding.editCategory.setOnClickListener(View.OnClickListener {
        editCategory(category)

    })

    binding.deleteCategory.setOnClickListener(View.OnClickListener {
        deleteCategory(category)
    })
}




    }

}

