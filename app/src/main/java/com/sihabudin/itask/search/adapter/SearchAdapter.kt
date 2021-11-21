package com.sihabudin.itask.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.utils.Common
import com.sihabudin.itask.core.utils.DateFormater
import com.sihabudin.itask.databinding.ItemTasksBinding

class SearchAdapter(
    private val onCheckedChange: (TaskWithCategoryModel, String) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var listSearchTask = ArrayList<TaskWithCategoryModel>()

    var onItemClick: ((TaskWithCategoryModel) -> Unit)? = null

    fun setData(newListTask: List<TaskWithCategoryModel>?) {
        if (newListTask == null) return
        listSearchTask.clear()
        listSearchTask.addAll(newListTask)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listSearchTask.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder = SearchViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_tasks, parent, false)
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = listSearchTask[position]
        holder.bind(data)
    }


    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTasksBinding.bind(itemView)
        fun bind(data: TaskWithCategoryModel) {
            binding.tvTitleTasks.text = data.title
            binding.tvCategoryTasks.text = data.category
            binding.tvDueDateTasks.text = data.due_date?.let {
                DateFormater.stringDateWithHourMinute(
                    it
                )
            }
            binding.imageColorTasks.setImageResource(Common.transformColorLabel(data.colorLabel.lowercase()))
            binding.checkTasks.isChecked = Common.setCheckedTask(data.status)

            binding.checkTasks.setOnClickListener {
                onCheckedChange(data, data.status)
            }


        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listSearchTask[adapterPosition])
            }
        }
    }


}