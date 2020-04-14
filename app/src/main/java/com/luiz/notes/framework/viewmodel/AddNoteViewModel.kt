package com.luiz.notes.framework.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.luiz.core.data.Note
import com.luiz.notes.presentation.base.BaseViewModel
import com.mlykotom.valifi.ValiFiForm
import com.mlykotom.valifi.fields.ValiFieldText
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteViewModel(application: Application) : BaseViewModel(application) {

    val title = ValiFieldText().addNotEmptyValidator("Campo obrigatório!")
    val description = ValiFieldText().addNotEmptyValidator("Campo obrigatório!")
    val form = ValiFiForm(title, description)

    //Dispatchers.IO é para trabalho com network
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val saved = MutableLiveData<Boolean>()
    val delete = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()
    val note = Note("", "", 0L, 0L)

    fun saveNote() {
        val time = System.currentTimeMillis()
        note.title = title.value.toString()
        note.content = description.value.toString()
        note.updatedTime = time
        if (note.id != 0L) {
            note.creationTime = time
        }

        coroutineScope.launch {
            useCases.addNote(note)
            saved.postValue(true)
        }
    }

    fun deleteNote(note: Note) {
        coroutineScope.launch {
            useCases.removeNote(note)
            delete.postValue(true)
        }
    }

    fun getNote(id: Long) {
        coroutineScope.launch {
            currentNote.postValue(useCases.getNote(id))
        }
    }
}