package com.example.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.todolist.ToDoApplication
import com.example.todolist.databinding.FragmentDetailBinding
import com.example.todolist.viewmodels.ToDoListViewModel
import com.example.todolist.viewmodels.ToDoListViewModelFactory

class DetailFragment : Fragment() {
    //private val navigationArgs: DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ToDoListViewModel by activityViewModels {
        ToDoListViewModelFactory(
            (activity?.application as ToDoApplication).database.toDoDao()
        )
    }
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            id = it.getInt("toDoAction_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //id = navigationArgs.actionId
        viewModel.retrieveAction(id).observe(viewLifecycleOwner){
            binding.itemOverall.text = it.action
            binding.itemDetails.text = it.detailedInfo
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}