package com.luiz.notes.framework.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.luiz.core.data.Note
import com.luiz.notes.presentation.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : BaseViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val list = MutableLiveData<List<Note>>()

    fun getAllNotes() {
        coroutineScope.launch {
            val notesList = useCases.getAllNotes()
            notesList.forEach {
                it.wordCount = useCases.getWordCount.invoke(it)
            }
            list.postValue(notesList)
        }
    }
}