package com.example.studentsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsapp.R
import com.example.studentsapp.data.model.User
import com.example.studentsapp.databinding.FragmentTeachersListBinding
import com.example.studentsapp.ui.fragments.adapters.TeachersListAdapter
import com.example.studentsapp.ui.fragments.viewmodels.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeachersListFragment : Fragment(R.layout.fragment_teachers_list), TeachersListAdapter.OnItemClickListener{

    private var _binding: FragmentTeachersListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTeachersListBinding.bind(view)

        val teachersAdapter = TeachersListAdapter(this)

        binding.apply {
            recyclerViewTeachers.apply {
                adapter = teachersAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.getStudents().observe(viewLifecycleOwner){
            teachersAdapter.submitList(it)
        }

    }

    interface OnItemClickListener{
        fun onClick(user: User)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(user: User) {
        TODO("Not yet implemented")
    }
}