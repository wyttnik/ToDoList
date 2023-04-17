package com.example.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.ItemAdapter
import com.example.todolist.data.DataSource
import com.example.todolist.databinding.FragmentTasksListBinding

class TasksListFragment : Fragment() {
    private var binding: FragmentTasksListBinding? = null
    private lateinit var recyclerView: RecyclerView
    private val itemList = DataSource.toDoItems

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding!!.recyclerView
        recyclerView.adapter = ItemAdapter(this.requireContext(), itemList)

        binding!!.addButton.setOnClickListener {
            val action = TasksListFragmentDirections.actionTasksListFragmentToItemToAddFragment()
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()

        binding!!.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}