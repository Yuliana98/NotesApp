package com.example.notesapp

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.notesapp.database.NotesDataBase
import com.example.notesapp.databinding.FragmentCreateNoteBinding
import com.example.notesapp.entities.Notes
import kotlinx.coroutines.launch
import java.util.Date

class CreateNoteFragment : BaseFragment() {
    var currentDate: String? = null
    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val simpleDateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = simpleDateFormat.format(Date())
//        val tvDateTime = view.findViewById<TextView>(R.id.tvDateTime)
//        val imgDone = view.findViewById<ImageView>(R.id.imgDone)
//        val imgBack = view.findViewById<ImageView>(R.id.imgBack)

        binding.tvDateTime.text = currentDate

        binding.imgDone.setOnClickListener {
            saveNote(view)
        }

        binding.imgBack.setOnClickListener {
            replaceFragment(HomeFragment.newInstance(), false)
        }
    }

    private fun saveNote(view: View) {
        /*val etNoteTitle = view.findViewById<EditText>(R.id.etNoteTitle)
        val etNoteSubTitle = view.findViewById<EditText>(R.id.etNoteSubTitle)
        val etNoteDesc = view.findViewById<EditText>(R.id.etNoteDesc)*/

        if (binding.etNoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        }

        if (binding.etNoteSubTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        }

        if (binding.etNoteDesc.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Description must is Required!", Toast.LENGTH_SHORT).show()
        }

        launch {
            val notes = Notes()
            notes.title = binding.etNoteTitle.text.toString()
            notes.subTitle = binding.etNoteSubTitle.text.toString()
            notes.noteText = binding.etNoteDesc.text.toString()
            notes.dateTime = currentDate
            context?.let {
                NotesDataBase.getDataBase(it).noteDao().insertNotes(notes)
                binding.etNoteTitle.setText("")
                binding.etNoteSubTitle.setText("")
                binding.etNoteDesc.setText("")
            }
        }
    }

    fun replaceFragment(fragment: Fragment, isTransition: Boolean) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (isTransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.add(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }
}