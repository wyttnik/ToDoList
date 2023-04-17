package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todolist.data.DataSource
import com.example.todolist.databinding.FragmentItemToAddBinding
import com.example.todolist.model.ToDoItem

class ItemToAddFragment : Fragment() {
    private val itemList = DataSource.toDoItems
    private var _binding: FragmentItemToAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemToAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmBtn.setOnClickListener {
            val ovText = binding.toAddItemOverall.text.toString().trim()
            val detText = binding.toAddItemDetails.text.toString().trim()
            if (ovText == "")
                Toast.makeText(context, "Please, enter what do you want to do", Toast.LENGTH_SHORT).show()
            else{
                itemList.add(ToDoItem(ovText,detText))
                findNavController().navigate(
                    ItemToAddFragmentDirections.actionItemToAddFragmentToTasksListFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}