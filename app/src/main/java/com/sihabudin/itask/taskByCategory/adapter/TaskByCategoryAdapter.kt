package com.sihabudin.itask.taskByCategory.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.utils.Common
import com.sihabudin.itask.core.utils.DateFormater
import com.sihabudin.itask.databinding.ItemTaskByCategoryBinding


class TaskByCategoryAdapter(
    private val onCheckedChange: (TaskWithCategoryModel, String) -> Unit
) : RecyclerView.Adapter<TaskByCategoryAdapter.TaskViewHolder>() {

    private var listTask = ArrayList<TaskWithCategoryModel>()

    var onItemClick: ((TaskWithCategoryModel) -> Unit)? = null

    fun setData(newListTask: List<TaskWithCategoryModel>?) {
        if (newListTask == null) return
        listTask.clear()
        listTask.addAll(newListTask)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listTask.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder = TaskViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_task_by_category, parent, false)
    )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val data = listTask[position]
        holder.bind(data)
    }


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTaskByCategoryBinding.bind(itemView)
        fun bind(data: TaskWithCategoryModel) {
            binding.tvTitleTaskCat.text = data.title
            binding.tvCategoryTaskCat.text = data.category
            binding.tvDueDateHistory.text = data.due_date?.let {
                DateFormater.stringDateWithHourMinute(
                    it
                )
            }
            binding.imageColorTaskCat.setImageResource(Common.transformColorLabel(data.colorLabel.lowercase()))
            binding.checkTaskCat.isChecked = Common.setCheckedTask(data.status)

            binding.checkTaskCat.setOnClickListener {
                onCheckedChange(data, data.status)
            }


        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listTask[adapterPosition])
            }
        }
    }


}