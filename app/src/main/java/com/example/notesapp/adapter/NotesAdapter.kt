package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemRecycleviewNotesBinding
import com.example.notesapp.entities.Notes

class NotesAdapter(val notesList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =
            ItemRecycleviewNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        with(holder) {
            with(notesList[position]) {
                binding.tvTitle.text = title
                binding.tvDesc.text = noteText
                binding.tvDateTime.text = dateTime
            }
        }
//        holder.itemView.findViewById<TextView>(R.id.tvTitle)

    }

    class NotesViewHolder(val binding: ItemRecycleviewNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}