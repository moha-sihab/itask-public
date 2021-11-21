package com.sihabudin.itask.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.CategoryWithCountTaskModel
import com.sihabudin.itask.core.utils.Common.Companion.transformColorLabel
import com.sihabudin.itask.databinding.ItemHomeBinding
import java.util.*

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    private var listCategory = ArrayList<CategoryWithCountTaskModel>()

    var onItemClick: ((CategoryWithCountTaskModel) -> Unit)? = null

    fun setData(newListData: List<CategoryWithCountTaskModel>?) {
        if (newListData == null) return
        listCategory.clear()
        listCategory.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        )

    override fun getItemCount() = listCategory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listCategory[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHomeBinding.bind(itemView)
        fun bind(data: CategoryWithCountTaskModel) {
            binding.tvItemHomeTitle.text = data.category
            binding.cardItemHome.setBackgroundResource(transformColorLabel(data.colorLabel.lowercase()))
            binding.tvItemHomeTotalTask.text = data.totalTask.toString()

        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listCategory[adapterPosition])
            }
        }
    }

}