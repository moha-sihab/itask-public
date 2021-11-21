package com.sihabudin.itask.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.TaskWithCategoryModel
import com.sihabudin.itask.core.utils.Common
import com.sihabudin.itask.core.utils.DateFormater
import com.sihabudin.itask.databinding.ItemTasksBinding

class TasksAdapter(
    private val onCheckedChange: (TaskWithCategoryModel, String) -> Unit
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

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
        LayoutInflater.from(parent.context).inflate(R.layout.item_tasks, parent, false)
    )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val data = listTask[position]
        holder.bind(data)
    }


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                onItemClick?.invoke(listTask[adapterPosition])
            }
        }
    }


}