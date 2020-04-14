package com.luiz.notes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.luiz.core.data.Note
import com.luiz.notes.R
import com.luiz.notes.databinding.ItemListNoteBinding
import com.luiz.notes.presentation.fragment.NotesFragmentDirections
import com.luiz.notes.presentation.interfaces.ListAction
import kotlinx.android.synthetic.main.fragment_notes.*

class NotesListAdapter(var notes: ArrayList<Note>) :
    RecyclerView.Adapter<NotesListAdapter.NotesViewHolder>(), ListAction {

    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemListNoteBinding>(
            inflater,
            R.layout.item_list_note,
            parent,
            false
        )
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val itemAtual = notes[position]
        holder.view.note = itemAtual
        holder.view.listener = this
    }

    class NotesViewHolder(var view: ItemListNoteBinding) : RecyclerView.ViewHolder(view.root)

    override fun onClick(v: View) {
        for (note in notes) {
            if (v.tag == note.id) {
                goToNoteDetail(note.id, v)
            }
        }
    }

    private fun goToNoteDetail(id: Long = 0L, v: View) {
        val action = NotesFragmentDirections.actionGoToNote(id)
        Navigation.findNavController(v).navigate(action)
    }
}