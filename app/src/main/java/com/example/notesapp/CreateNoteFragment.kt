package com.example.notesapp

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.util.Date

class CreateNoteFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
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
        val currentDate = simpleDateFormat.format(Date())
        val tvDateTime = view.findViewById<TextView>(R.id.tvDateTime)
        val imgDone = view.findViewById<ImageView>(R.id.imgDone)
        val imgBack = view.findViewById<ImageView>(R.id.imgBack)

        tvDateTime.text = currentDate

        imgDone.setOnClickListener {
            saveNote()
        }

        imgBack.setOnClickListener {
            replaceFragment(HomeFragment.newInstance(), false)
        }
    }

    private fun saveNote() {
//        TODO: save Note
    }

    fun replaceFragment(fragment: Fragment, isTransition: Boolean) {
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        if (isTransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.add(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }
}