package com.sihabudin.itask.detailTask.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.SubTaskModel
import com.sihabudin.itask.databinding.ItemSubTaskEditBinding

class SubTaskDetailAdapter : RecyclerView.Adapter<SubTaskDetailAdapter.ListViewHolder>() {
    private var listSubTask = ArrayList<SubTaskModel>()

    var onItemClick: ((SubTaskModel) -> Unit)? = null
    var onItemDeleteClick: ((SubTaskModel) -> Unit)? = null


    fun setData(newListData: List<SubTaskModel>?) {
        if (newListData == null) return
        listSubTask.clear()
        listSubTask.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listSubTask.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_sub_task_edit, parent, false)
    )

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSubTaskEditBinding.bind(itemView)
        fun bind(data: SubTaskModel) {
            binding.tvSubTaskDetail.text = data.subtask
            /*  binding.imbDeleteSubTaskDetail.setOnClickListener {
                  onDeleted(data)
              }*/
        }

        init {

            binding.imbDeleteSubTaskDetail.setOnClickListener {
                onItemDeleteClick?.invoke(
                    listSubTask[adapterPosition]
                )
            }
        }

    }

    fun deleteItem(subtask: SubTaskModel) {
        listSubTask.remove(subtask)
        Log.e("remove subtask=", subtask.subtask)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listSubTask[position]
        holder.bind(data)
    }


}