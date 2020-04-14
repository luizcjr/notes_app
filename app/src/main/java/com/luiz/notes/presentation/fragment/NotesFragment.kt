package com.luiz.notes.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.luiz.notes.R
import com.luiz.notes.databinding.FragmentNotesBinding
import com.luiz.notes.framework.viewmodel.NotesViewModel
import com.luiz.notes.presentation.adapter.NotesListAdapter
import com.luiz.notes.presentation.interfaces.ListAction
import com.luiz.notes.presentation.util.Utils
import kotlinx.android.synthetic.main.fragment_notes.*

/**
 * A simple [Fragment] subclass.
 */
class NotesFragment : Fragment() {

    private val notesListAdapter = NotesListAdapter(arrayListOf())
    private lateinit var viewModel: NotesViewModel
    private var binding: FragmentNotesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
        binding!!.fragment = this

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.list.observe(this, Observer { notesList ->
            if (notesList != null && notesList.isNotEmpty()) {
                binding!!.rvNotes.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = notesListAdapter
                }

                notesListAdapter.updateNotes(notesList.sortedByDescending {
                    it.updatedTime
                })
            } else {
                binding!!.rvNotes.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = Utils.noResultAdapter(
                        context!!,
                        context!!.getString(R.string.title_notes_empty),
                        R.drawable.ic_sad
                    )
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNotes()
    }

    fun goToNoteDetail() {
        val action = NotesFragmentDirections.actionGoToNote(0L)
        Navigation.findNavController(rvNotes).navigate(action)
    }
}
