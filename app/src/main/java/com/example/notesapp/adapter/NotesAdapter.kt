package com.example.notesapp.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemRecycleviewNotesBinding
import com.example.notesapp.entities.Notes

class NotesAdapter() :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var listener: OnItemClickListener? = null
    var notesList = ArrayList<Notes>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =
            ItemRecycleviewNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setData(arrNotesList: List<Notes>) {
        notesList = arrNotesList as ArrayList<Notes>
    }

    fun setOnClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        with(holder) {
            with(notesList[position]) {
                binding.tvTitle.text = title
                binding.tvDesc.text = noteText
                binding.tvDateTime.text = dateTime
            }
            if (notesList[position].color != null) {
                binding.cardView.setCardBackgroundColor(Color.parseColor(notesList[position].color))
            } else {
                binding.cardView.setCardBackgroundColor(Color.parseColor("#222B3C"))
            }

//            TODO: impPath, webLink
            if (notesList[position].imgPath != null) {
                binding.imgNote.setImageBitmap(BitmapFactory.decodeFile(notesList[position].imgPath))
                binding.imgNote.visibility = View.VISIBLE
            } else {
                binding.imgNote.visibility = View.GONE
            }

            if (notesList[position].webLink != "") {
                binding.tvWebLink.text = notesList[position].webLink
                binding.tvWebLink.visibility = View.VISIBLE
            } else {
                binding.tvWebLink.visibility = View.GONE
            }

            binding.cardView.setOnClickListener {
                listener!!.onClicked(notesList[position].id!!)
            }
        }

    }

    class NotesViewHolder(val binding: ItemRecycleviewNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface OnItemClickListener {
        fun onClicked(noteId: Int)
    }
}