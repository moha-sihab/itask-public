package com.sihabudin.itask.category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.utils.Common
import com.sihabudin.itask.databinding.ItemCategoryListBinding

class CategoryListAdapter(
    private val onSwitchChange: (CategoryModel, Boolean) -> Unit
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    private var listCategory = ArrayList<CategoryModel>()

    var onItemClick: ((CategoryModel) -> Unit)? = null

    fun setData(newListCategory: List<CategoryModel>?) {
        if (newListCategory == null) return
        listCategory.clear()
        listCategory.addAll(newListCategory)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listCategory.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder = CategoryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_category_list, parent, false)
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val data = listCategory[position]
        holder.bind(data)
    }


    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCategoryListBinding.bind(itemView)
        fun bind(data: CategoryModel) {
            binding.tvCategory.text = data.category
            binding.imageColorCategory.setImageResource(Common.transformColorLabel(data.colorLabel.lowercase()))
            binding.switchActiveCategory.isChecked = data.isActive

            binding.switchActiveCategory.setOnClickListener {
                onSwitchChange(data, data.isActive)
            }


        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listCategory[adapterPosition])
            }
        }
    }


}