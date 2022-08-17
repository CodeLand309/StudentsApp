package com.example.studentsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.studentsapp.R
import com.example.studentsapp.data.model.User
import com.example.studentsapp.databinding.FragmentTeacherDetailsBinding
import com.example.studentsapp.ui.fragments.adapters.TeachersListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherDetailsFragment: Fragment(R.layout.fragment_teacher_details) {

    private var _binding: FragmentTeacherDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTeacherDetailsBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}