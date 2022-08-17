package com.example.studentsapp.ui.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.data.model.User
import com.example.studentsapp.databinding.CardTeacherBinding
import com.example.studentsapp.dependency.AppModule

class TeachersListAdapter(private val listener: OnItemClickListener): ListAdapter<User,TeachersListAdapter.TeacherViewHolder>(DiffCallback()) {

    inner class TeacherViewHolder(private val binding: CardTeacherBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION){
                        val user = getItem(position)
                        listener.onClick(user)
                    }
                }
            }
        }

        fun bind(user: User){
            binding.apply {
                textName.text = user.name
                if (AppModule.UserType.Teacher.name == user.userType)
                    imgStar.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val binding = CardTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val teacher = getItem(position)
        holder.bind(teacher)
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