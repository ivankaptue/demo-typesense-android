package com.klid.demotypesense.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.klid.demotypesense.R
import com.klid.demotypesense.api.model.Student

/**
 * @author Ivan Kaptue
 */
class StudentAdapter : PagedListAdapter<Student, StudentAdapter.StudentViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean =
            oldItem == newItem
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val idTextView: TextView = itemView.findViewById(R.id.id_text_view)
        private val firstnameTextView: TextView = itemView.findViewById(R.id.firstname_text_view)
        private val lastnameTextView: TextView = itemView.findViewById(R.id.lastname_text_view)
        private val emailTextView: TextView = itemView.findViewById(R.id.email_text_view)
        private val schoolTextView: TextView = itemView.findViewById(R.id.school_text_view)

        fun bind(student: Student) {
            idTextView.text = String.format("#%s", student.id.toString())
            firstnameTextView.text = student.firstname
            lastnameTextView.text = student.lastname
            emailTextView.text = student.email
            schoolTextView.text = student.school
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}
