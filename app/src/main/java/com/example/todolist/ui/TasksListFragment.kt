package com.example.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.ToDoApplication
import com.example.todolist.adapter.ActionsAdapter
import com.example.todolist.data.entity.ToDoAction
import com.example.todolist.databinding.FragmentTasksListBinding
import com.example.todolist.viewmodels.ToDoListViewModel
import com.example.todolist.viewmodels.ToDoListViewModelFactory


class TasksListFragment : Fragment() {
    private var binding: FragmentTasksListBinding? = null
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ToDoListViewModel by activityViewModels{
        ToDoListViewModelFactory((activity?.application as ToDoApplication).database.toDoDao())
    }

    data class Test (
        val onActionClick: (index: Int) -> Unit,
        val onCheckBoxClick: (toDoAction: ToDoAction) -> Unit,
        val onDeleteClick: (toDoAction: ToDoAction) -> Unit,
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    private fun clickOnActionHandler(id:Int) {
        val action = TasksListFragmentDirections.actionTasksListFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }

    private fun clickOnCheckBoxHandler(toDoAction: ToDoAction) {
        viewModel.updateActionCheckStatus(toDoAction)
    }

    private fun clickOnDeleteHandler(toDoAction: ToDoAction) {
        viewModel.deleteAction(toDoAction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding!!.recyclerView
        val adapter = ActionsAdapter(Test(
            { clickOnActionHandler(it) },
            { clickOnCheckBoxHandler(it) },
            { clickOnDeleteHandler(it) }
        ))

        recyclerView.adapter = adapter

        binding!!.addButton.setOnClickListener {
            val action = TasksListFragmentDirections.actionTasksListFragmentToItemToAddFragment()
            findNavController().navigate(action)
        }
        viewModel.allActions.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}