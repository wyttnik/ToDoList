package com.example.todolist.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.fragments.TasksListFragmentDirections
import com.example.todolist.model.ToDoItem

class ItemAdapter(private val context: Context, private val dataset: MutableList<ToDoItem>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView:TextView = view.findViewById(R.id.item)
        val checkBox:CheckBox = view.findViewById(R.id.checkBox)
        val deleteBtn:ImageButton = view.findViewById(R.id.removeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemAdapter.ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_item, parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.action

        holder.checkBox.setOnClickListener {
            item.checkStatus = !item.checkStatus
            // maybe it's better to use notifyitemchanged instead of manually write changes
            if (item.checkStatus) holder.textView.paintFlags = holder.textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else holder.textView.paintFlags = holder.textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.deleteBtn.setOnClickListener {
            dataset.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }

        holder.textView.setOnClickListener {
            val action = TasksListFragmentDirections.actionTasksListFragmentToDetailFragment(
                overallInfo = item.action, details = item.detailedInfo)
            holder.textView.findNavController().navigate(action)
        }

        holder.checkBox.isChecked = item.checkStatus
        if (item.checkStatus) holder.textView.paintFlags = holder.textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else holder.textView.paintFlags = holder.textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }

    override fun getItemCount():Int {
        return dataset.size
    }

}