package com.sihabudin.itask.addTask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.SubTaskModel
import com.sihabudin.itask.databinding.ItemSubTaskBinding

class SubTaskAdapter : RecyclerView.Adapter<SubTaskAdapter.ListViewHolder>() {
    private var listSubTask = ArrayList<SubTaskModel>()

    var onItemClick: ((SubTaskModel) -> Unit)? = null
    var onItemDeleteClick: ((SubTaskModel) -> Unit)? = null


    fun setData(newListSubTask: List<SubTaskModel>?) {
        if (newListSubTask == null) return
        listSubTask.clear()
        listSubTask.addAll(newListSubTask)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listSubTask.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_sub_task, parent, false)
    )

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSubTaskBinding.bind(itemView)
        fun bind(data: SubTaskModel) {
            binding.tvSubTask.text = data.subtask
        }

        init {

            binding.imbDeleteSubTask.setOnClickListener { onItemDeleteClick?.invoke(listSubTask[adapterPosition]) }
        }

    }

    fun deleteItem(subtask: SubTaskModel) {
        listSubTask.remove(subtask)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listSubTask[position]
        holder.bind(data)
    }


}