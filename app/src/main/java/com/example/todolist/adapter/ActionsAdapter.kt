/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.todolist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.entity.ToDoAction
import com.example.todolist.databinding.TodoItemBinding
import com.example.todolist.ui.TasksListFragment

class ActionsAdapter(private val toDoCallbacks: TasksListFragment.Test): ListAdapter<ToDoAction, ActionsAdapter.ActionViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ToDoAction>() {
            override fun areItemsTheSame(oldItem: ToDoAction, newItem: ToDoAction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ToDoAction, newItem: ToDoAction): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        val todoItem = getItem(position)
        holder.textView.text = todoItem.action
        if (todoItem.checkStatus) holder.textView.paintFlags = holder.textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else holder.textView.paintFlags = holder.textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        holder.checkBox.isChecked = todoItem.checkStatus
        holder.textView.setOnClickListener{ toDoCallbacks.onActionClick(todoItem.id) }
        holder.checkBox.setOnClickListener{ toDoCallbacks.onCheckBoxClick(todoItem) }
        holder.deleteBtn.setOnClickListener { toDoCallbacks.onDeleteClick(todoItem) }
    }

    class ActionViewHolder(binding: TodoItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val textView = binding.item
        val checkBox = binding.checkBox
        val deleteBtn = binding.removeButton
    }
}
