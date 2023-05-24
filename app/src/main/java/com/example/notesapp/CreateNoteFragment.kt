package com.example.notesapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notesapp.database.NotesDataBase
import com.example.notesapp.databinding.FragmentCreateNoteBinding
import com.example.notesapp.entities.Notes
import com.example.notesapp.utils.NotesBottomSheetFragment
import com.example.notesapp.utils.NotesBottomSheetFragment.Companion.noteId
import kotlinx.coroutines.launch
import java.util.Date

class CreateNoteFragment : BaseFragment() {
    var currentDate: String? = null
    private var noteId = -1
    var selectedColor = "#171C26"

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!
    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteId = requireArguments().getInt("noteId",-1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val simpleDateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

        currentDate = simpleDateFormat.format(Date())
        binding.tvDateTime.text = currentDate
        binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

        binding.imgDone.setOnClickListener {
            println("noteId =  $noteId ")
            if (noteId != -1) {
                updateNote()
            } else {
                saveNote()
            }
        }

        binding.imgBack.setOnClickListener {
            replaceFragment(HomeFragment.newInstance(), false)
        }

        binding.imgMore.setOnClickListener {
            var notesBottomSheetFragment = NotesBottomSheetFragment.newInstance(id)
            notesBottomSheetFragment.show(
                requireActivity().supportFragmentManager,
                "Note Bottom Sheet Fragment"
            )
        }
    }

    private fun saveNote() {

        if (binding.etNoteTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()

        } else if (binding.etNoteSubTitle.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()

        } else if (binding.etNoteDesc.text.isNullOrEmpty()) {
            Toast.makeText(context, "Note Description must is Required!", Toast.LENGTH_SHORT).show()

        } else {

            launch {
                val notes = Notes()
                notes.title = binding.etNoteTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDesc.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
                context?.let {
                    NotesDataBase.getDataBase(it).noteDao().insertNotes(notes)
                    binding.etNoteTitle.setText("")
                    binding.etNoteSubTitle.setText("")
                    binding.etNoteDesc.setText("")
                    binding.layoutImage.visibility = View.GONE
                    binding.imgNote.visibility = View.GONE
                    binding.tvWebLink.visibility = View.GONE
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }

    }

    private fun updateNote(){
        launch {

            context?.let {
                var notes = NotesDataBase.getDataBase(it).noteDao().getSpecificNote(noteId)

                notes.title = binding.etNoteTitle.text.toString()
                notes.subTitle = binding.etNoteSubTitle.text.toString()
                notes.noteText = binding.etNoteDesc.text.toString()
                notes.dateTime = currentDate
                notes.color = selectedColor
                /*notes.imgPath = binding.selectedImagePath
                notes.webLink = binding.webLink*/

                NotesDataBase.getDataBase(it).noteDao().updateNote(notes)
                binding.etNoteTitle.setText("")
                binding.etNoteSubTitle.setText("")
                binding.etNoteDesc.setText("")
                binding.layoutImage.visibility = View.GONE
                binding.imgNote.visibility = View.GONE
                binding.tvWebLink.visibility = View.GONE
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
    }

    fun replaceFragment(fragment: Fragment, isTransition: Boolean) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (isTransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.add(R.id.frame_layout, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }

    private val BroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

            var actionColor = p1!!.getStringExtra("action")

            when (actionColor!!) {

                "Blue" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }

                "Yellow" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Purple" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Green" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Orange" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }


                "Black" -> {
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }

                else -> {
                    binding.layoutImage.visibility = View.GONE
                    binding.imgNote.visibility = View.GONE
                    selectedColor = p1.getStringExtra("selectedColor")!!
                    binding.colorView.setBackgroundColor(Color.parseColor(selectedColor))

                }
            }
        }

    }

}
