package com.luiz.notes.presentation.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.luiz.core.data.Note

import com.luiz.notes.R
import com.luiz.notes.databinding.AddNoteBinding
import com.luiz.notes.framework.viewmodel.AddNoteViewModel
import com.luiz.notes.presentation.util.Utils
import kotlinx.android.synthetic.main.fragment_add_note.*

/**
 * A simple [Fragment] subclass.
 */
class AddNoteFragment : Fragment() {

    private lateinit var viewModel: AddNoteViewModel
    private var currentNote = Note("", "", 0L, 0L)
    private var noteId = 0L
    private var binding: AddNoteBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_note, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)

        binding!!.addNoteViewModel = viewModel
        binding!!.lifecycleOwner = this

        arguments?.let {
            noteId = AddNoteFragmentArgs.fromBundle(it).idNote
        }

        if (noteId != 0L) {
            viewModel.getNote(noteId)
            setHasOptionsMenu(true)
        }

        observerViewModel()
    }

    private fun observerViewModel() {
        viewModel.saved.observe(this, Observer {
            if (it) {
                Toast.makeText(context, "Anotação adicionada com sucesso!", Toast.LENGTH_SHORT)
                    .show()
                Utils.hideKeyboard(binding!!.etTitle)
                Navigation.findNavController(binding!!.etTitle).popBackStack()
            } else {
                Toast.makeText(
                    context,
                    "Ocorreu um erro. Tente novamente, mais tarde!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.delete.observe(this, Observer {
            if (it) {
                Toast.makeText(context, "Anotação deletada com sucesso!", Toast.LENGTH_SHORT)
                    .show()
                Utils.hideKeyboard(binding!!.etTitle)
                Navigation.findNavController(binding!!.etTitle).popBackStack()
            } else {
                Toast.makeText(
                    context,
                    "Ocorreu um erro. Tente novamente, mais tarde!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.currentNote.observe(this, Observer {
            it?.let {
                currentNote = it
                viewModel.title.value = it.title
                viewModel.description.value = it.content
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuDelete -> {
                if (context != null && noteId != 0L) {
                    AlertDialog.Builder(context!!)
                        .setTitle("Deletar anotação")
                        .setMessage("Tem certeza que deseja deletar essa anotação?")
                        .setPositiveButton("Sim") { dialogInterface, i ->
                            viewModel.deleteNote(currentNote)
                        }.setNegativeButton("Cancelar") { dialogInterface, i ->
                            dialogInterface.dismiss()
                        }.create().show()
                }
            }
        }
        return true
    }
}
