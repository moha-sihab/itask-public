package com.sihabudin.itask.category.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.utils.Common
import com.sihabudin.itask.databinding.ItemCategoryListSelectedBinding
import java.util.*

class CategoryListSelectedAdapter :
    RecyclerView.Adapter<CategoryListSelectedAdapter.ListViewHolder>() {
    private var listCategory = ArrayList<CategoryModel>()

    var onItemClick: ((CategoryModel) -> Unit)? = null

    fun setData(newListData: List<CategoryModel>?) {
        if (newListData == null) return
        listCategory.clear()
        listCategory.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category_list_selected, parent, false)
        )

    override fun getItemCount() = listCategory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listCategory[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCategoryListSelectedBinding.bind(itemView)
        fun bind(data: CategoryModel) {
            binding.tvCategoryCls.text = data.category
            binding.imageColorCls.setImageResource(Common.transformColorLabel(data.colorLabel.lowercase()))

        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listCategory[adapterPosition])
            }
        }
    }
}