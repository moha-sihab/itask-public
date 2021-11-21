package com.sihabudin.itask.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.utils.Common
import com.sihabudin.itask.core.utils.DateFormater
import com.sihabudin.itask.databinding.ItemHistoryBinding


class HistoryAdapter(private val onCheckedChange: (TaskWithCategoryModel, String) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    private var listHistory = ArrayList<TaskWithCategoryModel>()

    var onItemClick: ((TaskWithCategoryModel) -> Unit)? = null

    fun setData(newListData: List<TaskWithCategoryModel>?) {
        if (newListData == null) return
        listHistory.clear()
        listHistory.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listHistory.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
    )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var data = listHistory[position]
        holder.bind(data)
    }


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemHistoryBinding.bind(itemView)
        fun bind(data: TaskWithCategoryModel) {
            binding.tvTitleHistory.text = data.title
            binding.tvCategoryHistory.text = data.category
            binding.tvDueDateHistory.text = data.due_date?.let {
                DateFormater.stringDateWithHourMinute(
                    it
                )
            }
            binding.imageColorHistory.setImageResource(Common.transformColorLabel(data.colorLabel.lowercase()))
            binding.checkHistory.isChecked = Common.setCheckedTask(data.status)
            binding.checkHistory.setOnClickListener {
                onCheckedChange(data, data.status)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listHistory[adapterPosition])
            }
        }
    }
}