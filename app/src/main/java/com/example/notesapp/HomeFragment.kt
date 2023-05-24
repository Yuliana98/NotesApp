package com.example.notesapp

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.adapter.NotesAdapter
import com.example.notesapp.database.NotesDataBase
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.entities.Notes
import kotlinx.coroutines.launch
import java.util.Locale

class HomeFragment : BaseFragment() {
    var notesList = ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()
    private var _binding: FragmentHomeBinding? = null
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                var notes = NotesDataBase.getDataBase(it).noteDao().getAllNotes()
                notesAdapter!!.setData(notes)
                notesList = notes as ArrayList<Notes>
                //set the adapter in recycler_view
                binding.recyclerView.adapter = notesAdapter
            }
        }

        notesAdapter!!.setOnClickListener(onClicked)

        binding.fabBtnCreateNote.setOnClickListener {
            replaceFragment(CreateNoteFragment.newInstance(), true)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                var tempArr = ArrayList<Notes>()

                for (arr in notesList){
                    if (arr.title!!.lowercase(Locale.getDefault()).contains(p0.toString())){
                        tempArr.add(arr)
                    }
                }

                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()
                return true
            }

        })

    }

    private val onClicked = object :NotesAdapter.OnItemClickListener{
        override fun onClicked(notesId: Int) {


            var fragment :Fragment
            var bundle = Bundle()
            bundle.putInt("noteId",notesId)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle

            replaceFragment(fragment,false)
        }

    }


    private fun replaceFragment(fragment: Fragment, isTransition: Boolean) {
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

}