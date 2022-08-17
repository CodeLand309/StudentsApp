package com.example.studentsapp.ui.fragments.adapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.data.model.User
import com.example.studentsapp.databinding.CardStudentBinding

class StudentsListAdapter(private val listener: OnItemClickListener): ListAdapter<User, StudentsListAdapter.StudentViewHolder>(DiffCallback()) {

    inner class StudentViewHolder(private val binding: CardStudentBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply {
                root.setOnClickListener{
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val user = getItem(position)
                        listener.onClick(user)
                    }
                }
            }
        }
        fun bind(user: User) {
            binding.apply {
                textStudName.text = user.name
                textStudName.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = CardStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentStudent = getItem(position)
        holder.bind(currentStudent)
    }

    interface OnItemClickListener{
        fun onClick(user: User)
    }

    class DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem
    }
}