package com.example.studentsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsapp.R
import com.example.studentsapp.data.model.User
import com.example.studentsapp.databinding.FragmentStudentsListBinding
import com.example.studentsapp.ui.fragments.adapters.StudentsListAdapter
import com.example.studentsapp.ui.fragments.viewmodels.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentsListFragment: Fragment(R.layout.fragment_students_list),
    StudentsListAdapter.OnItemClickListener {

    private var _binding: FragmentStudentsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentStudentsListBinding.bind(view)

        val studentAdapter = StudentsListAdapter(this)

        binding.apply {
            recyclerViewStudents.apply {
                adapter = studentAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.getStudents().observe(viewLifecycleOwner){
            studentAdapter.submitList(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(user: User) {
        TODO("Not yet implemented")
    }

}